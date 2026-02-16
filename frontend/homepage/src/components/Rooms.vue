<script setup>
import { ref, onMounted } from "vue";

// Data
const rooms = ref([]);

// Panel-States
const showFilterPanel = ref(false);
const showAddRoomPanel = ref(false);
const showEditRoomPanel = ref(false);

// Filter-Variables
const filterId = ref('');
const filterRoomSize = ref('');
const filterHasMinibar = ref('');
const filterIsOccupied = ref('');

// Form-Variables (Add/Edit)
const newRoomNumber = ref('');
const newRoomSize = ref('');
const newHasMinibar = ref('');
const newIsOccupied = ref('');

onMounted(() => {
  loadRooms();
});

const loadRooms = () => {
  fetch('http://localhost:8080/rest/rooms/list')
      .then(response => response.json())
      .then(data => {
        console.log("Backend data:", data);
        rooms.value = data;
      })
      .catch(error => console.error("Fehler:", error));
}

const filterRooms = () => {
  const filterData = {
    roomNumber: filterId.value ? Number(filterId.value) : null,
    roomSize: filterRoomSize.value || null,
    hasMinibar: filterHasMinibar.value === "" ? null : (filterHasMinibar.value === "true"),
    occupied: filterIsOccupied.value === "" ? null : (filterIsOccupied.value === "true")
  };

  fetch('http://localhost:8080/rest/rooms/filter', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(filterData)
  })
      .then(response => response.json())
      .then(data => {
        rooms.value = data;
        showFilterPanel.value = false;
      })
      .catch(error => console.error("Filter-Fehler:", error));
}

const deleteRoom = (roomNumber) => {
  if (!confirm(`Zimmer ${roomNumber} wirklich löschen?`)) return;
  fetch(`http://localhost:8080/rest/rooms/delete?id=${roomNumber}`, { method: 'DELETE' })
      .then(response => { if (response.ok) loadRooms(); })
      .catch(error => console.error("Fehler beim Löschen:", error));
}

const editRoom = (roomNumber) => {
  const roomToEdit = rooms.value.find(r => r.roomNumber === roomNumber);
  if (roomToEdit) {
    newRoomNumber.value = roomToEdit.roomNumber;
    newRoomSize.value = "";
    newHasMinibar.value = "";
    newIsOccupied.value = "";
    showEditRoomPanel.value = true;
  }
}

const editRoomsSave = () => {
  const editData = {
    roomNumber: Number(newRoomNumber.value),
    roomSize: newRoomSize.value || null,
    hasMinibar: newHasMinibar.value === "" ? null : (newHasMinibar.value === "true"),
    occupied: newIsOccupied.value === "" ? null : (newIsOccupied.value === "true")
  };

  fetch('http://localhost:8080/rest/rooms/edit', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(editData)
  })
      .then(response => {
        if (response.ok) {
          showEditRoomPanel.value = false;
          loadRooms();
        }
      });
}

const safeAndAddRoom = () => {
  const roomData = {
    roomNumber: Number(newRoomNumber.value),
    roomSize: newRoomSize.value,
    hasMinibar: newHasMinibar.value === "true",
    occupied: newIsOccupied.value === "false"
  };

  fetch('http://localhost:8080/rest/rooms/add', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(roomData)
  })
      .then(response => {
        if (response.ok) {
          showAddRoomPanel.value = false;
          loadRooms();

          newRoomNumber.value = '';
          newRoomSize.value = '';
        }
      });
}
</script>

<template>
  <div class="rooms-view">
    <h1>Zimmerverwaltung</h1>
    <div class="action-bar">
      <button @click="showFilterPanel = true" class="filter-trigger-btn">🔍 Filter</button>
      <button @click="showAddRoomPanel = true" class="add-btn">➕ Neues Zimmer</button>
    </div>

    <div v-if="showFilterPanel" class="panel-overlay">
      <div class="panel-content">
        <h2>Filter</h2>
        <div class="input-group">
          <input type="number" v-model="filterId" placeholder="Zimmer ID">
          <select v-model="filterRoomSize">
            <option value="">Alle Größen</option>
            <option value="EINZELZIMMER">Einzelzimmer</option>
            <option value="DOPPELZIMMER">Doppelzimmer</option>
            <option value="SUITE">Suite</option>
          </select>
          <select v-model="filterHasMinibar">
            <option value="">Egal</option>
            <option value="true">Minibar</option>
            <option value="false">Keine Minibar</option>
          </select>
          <select v-model="filterIsOccupied">
            <option value="">Egal</option>
            <option value="true">Belegt</option>
            <option value="false">Frei</option>
          </select>
        </div>
        <div class="panel-actions">
          <button @click="filterRooms">Anwenden</button>
          <button @click="showFilterPanel = false">Abbrechen</button>
        </div>
      </div>
    </div>

    <table class="room-table">
      <thead>
      <tr>
        <th>Zimmernummer</th>
        <th>Zimmergröße</th>
        <th>Minibar</th>
        <th>Status</th>
        <th>Aktionen</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="room in rooms" :key="room.roomNumber">
        <td>{{ room.roomNumber }}</td>
        <td class="capitalize">{{ room.roomSize }}</td>
        <td>{{ room.hasMinibar ? '✅' : '❌' }}</td>
        <td>
            <span :class="room.occupied ? 'status-occupied' : 'status-free'">
                {{ room.occupied ? 'Belegt' : 'Frei' }}
            </span>
        </td>
        <td>
          <button @click="editRoom(room.roomNumber)">✏️</button>
          <button @click="deleteRoom(room.roomNumber)" class="btn-delete">🗑️</button>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showEditRoomPanel" class="panel-overlay">
      <div class="panel-content">
        <h2>Zimmer {{ newRoomNumber }} bearbeiten</h2>
        <select v-model="newRoomSize">
          <option value="">Unverändert</option>
          <option value="EINZELZIMMER">Einzelzimmer</option>
          <option value="DOPPELZIMMER">Doppelzimmer</option>
          <option value="SUITE">Suite</option>
        </select>
        <select v-model="newHasMinibar">
          <option value="">Unverändert</option>
          <option value="true">Hat Minibar</option>
          <option value="false">Keine Minibar</option>
        </select>
        <select v-model="newIsOccupied">
          <option value="">Unverändert</option>
          <option value="true">Belegt</option>
          <option value="false">Frei</option>
        </select>
        <div class="panel-actions">
          <button @click="editRoomsSave">Speichern</button>
          <button @click="showEditRoomPanel = false">Abbrechen</button>
        </div>
      </div>
    </div>

    <div v-if="showAddRoomPanel" class="panel-overlay">
      <div class="panel-content">
        <h2>Neues Zimmer</h2>
        <input type="number" v-model="newRoomNumber" placeholder="Nummer">
        <select v-model="newRoomSize">
          <option value="EINZELZIMMER">Einzelzimmer</option>
          <option value="DOPPELZIMMER">Doppelzimmer</option>
          <option value="SUITE">Suite</option>
        </select>
        <select v-model="newHasMinibar">
          <option value="true">Hat Minibar</option>
          <option value="false">Keine Minibar</option>
        </select>
        <div class="panel-actions">
          <button @click="safeAndAddRoom">Hinzufügen</button>
          <button @click="showAddRoomPanel = false">Abbrechen</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.rooms-view {
  animation: fadeIn 0.3s ease;
  width: 100vw;
  position: relative;
  left: 50%;
  right: 50%;
  margin-left: -50vw;
  margin-right: -50vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem 0;
}

.room-table {
  /* Festgelegte Breite oder Max-Width verhindert, dass sie "flattert" */
  width: 90%;
  max-width: 800px;
  border-collapse: collapse;
  margin-top: 2rem;
  /* Sorgt für den dunklen Karten-Look aus deinem Screen */
  background: #242424;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.5);
}

.action-bar {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 1rem;
}

/* Damit die Spalten gleichmäßig wirken */
.room-table th, .room-table td {
  padding: 16px;
  border-bottom: 1px solid #333;
  text-align: center;
  width: 20%; /* Verteilt 5 Spalten gleichmäßig auf je 20% */
}

/* ... Rest deiner Styles (status-occupied, panel-overlay, etc.) bleiben gleich ... */
.status-occupied { color: #e74c3c; font-weight: bold; }
.status-free { color: #2ecc71; font-weight: bold; }

.panel-overlay {
  position: fixed; top: 0; left: 0; width: 100%; height: 100%;
  background: rgba(0,0,0,0.7); display: flex; justify-content: center; align-items: center;
  z-index: 2000;
}

.panel-content {
  background: #2c3e50;
  color: white;
  padding: 2rem;
  border-radius: 8px;
  min-width: 350px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  box-shadow: 0 4px 15px rgba(0,0,0,0.5);
}

.capitalize { text-transform: lowercase; }

.capitalize::first-letter {
  text-transform: uppercase;
}

@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
</style>