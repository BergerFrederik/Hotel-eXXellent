<script setup>
import { ref, onMounted } from 'vue'


const aktuelleAnsicht = ref('home')
const rooms = ref([])

const showFilterPanel = ref(false)
const filterId = ref('')
const filterRoomSize = ref('');
const filterHasMinibar = ref(false);
const filterIsOccupied = ref(false);

const showAddRoomPanel = ref(false);
const newRoomNumber = ref('');
const newRoomSize = ref('');
const newHasMinibar = ref(false);
const newIsOccupied = ref(false);

const showEditRoomPanel = ref(false);

const ladeZimmer = () => {
  aktuelleAnsicht.value = 'liste'

  // Die eigentliche Anfrage
  fetch('http://localhost:8080/rest/rooms')
      .then(response => response.json())
      .then(data => {
        console.log("Daten vom Backend:", data)
        rooms.value = data
      })
      .catch(error => console.error("Oje, Fehler:", error))
}

const filterRooms = () => {
  aktuelleAnsicht.value = 'liste'

  const filterData = {
    roomNumber: filterId.value ? Number(filterId.value) : null,
    roomSize: filterRoomSize.value || null,
    hasMinibar: filterHasMinibar.value === "" ? null : (filterHasMinibar.value === "true"),
    isOccupied: filterIsOccupied.value === "" ? null : (filterIsOccupied.value === "true")
  };

  // Die eigentliche Anfrage
  fetch('http://localhost:8080/rest/rooms/filter', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(filterData)
  })
      .then(response => response.json())
      .then(data => {
        console.log("Daten vom Backend:", data)
        rooms.value = data;
        showFilterPanel.value = false;
      })
      .catch(error => console.error("Oje, Fehler:", error))

}

const editRooms = () => {
  aktuelleAnsicht.value = 'liste'

  const editData = {
    roomNumber: newRoomNumber.value ? Number(newRoomNumber.value) : null,
    roomSize: newRoomSize.value || null,
    hasMinibar: newHasMinibar.value === "" ? null : (newHasMinibar.value === "true"),
    isOccupied: newIsOccupied.value === "" ? null : (newIsOccupied.value === "true")
  };

  // Die eigentliche Anfrage
  fetch('http://localhost:8080/rest/rooms/edit', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(editData)
  })
      .then(response => {
        if (response.ok) {
          console.log("Änderung erfolgreich");
          showEditRoomPanel.value = false;
          filterId.value = '';
          ladeZimmer();
        }
      })
      .catch(error => console.error("Fehler:", error));
}

const deleteRoom = (roomNumber) => {
  if (!confirm(`Möchtest du Zimmer ${roomNumber} wirklich löschen?`)) return;

  fetch(`http://localhost:8080/rest/rooms/delete?id=${roomNumber}`, {
    method: 'DELETE',
  })
      .then(response => {
        if (response.ok) {
          console.log('Deleting room');
          ladeZimmer();
        }
      })
      .catch(error => console.error("Oje, Fehler:", error));
}

const editRoom = (roomNumber) => {
  // 1. Filter-Variablen vorbereiten
  filterRoomSize.value = '';
  filterHasMinibar.value = '';
  filterIsOccupied.value = '';
  filterId.value = roomNumber;

  // 2. Filtern starten
  filterRooms();

  // 3. Edit-Panel öffnen
  showEditRoomPanel.value = true;

  // 4. Das Zimmer in der aktuellen Liste suchen
  const roomToEdit = rooms.value.find(r => r.roomNumber === roomNumber);

  // 5. Nur zuweisen, wenn das Zimmer auch wirklich gefunden wurde!
  if (roomToEdit) {
    newRoomNumber.value = roomToEdit.roomNumber;
    newRoomSize.value = roomToEdit.roomSize;

    // Nutze ?. und einen Fallback (""), falls Werte null sind
    newHasMinibar.value = roomToEdit.hasMinibar?.toString() ?? "";
    newIsOccupied.value = roomToEdit.isOccupied?.toString() ?? "";
  } else {
    // Falls die Liste noch lädt, setzen wir die ID zumindest schon mal fest
    newRoomNumber.value = roomNumber;
    newRoomSize.value = '';
    newHasMinibar.value = '';
    newIsOccupied.value = '';
  }
}

const safeAndAddRoom = () => {
  const roomData = {
    roomNumber: Number(newRoomNumber.value),
    roomSize: newRoomSize.value,
    hasMinibar: Boolean(newHasMinibar.value),
    isOccupied: Boolean(newIsOccupied.value)
  };

  fetch('http://localhost:8080/rest/rooms/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(roomData)
  })
      .then(response => {
        if (response.ok) {
          console.log('Zimmer erfolgreich hinzugefügt');
          showAddRoomPanel.value = false;
          ladeZimmer();

          newRoomNumber.value = '';
          newRoomSize.value = '';
          newHasMinibar.value = false;
        }
      })
      .catch(error => console.error("Fehler:", error));
}

</script>

<template>
  <div class="hotel-manager">
    <nav class="navbar">
      <div class="logo">🏨 eXXellent Nights</div>
      <div class="nav-links">
        <button @click="aktuelleAnsicht = 'home'">Homepage</button>
        <button @click="ladeZimmer">Zimmerliste</button>
        <button @click="filterRooms(1)">Test</button>
        <button @click="aktuelleAnsicht = 'verwaltung'">Zimmerverwaltung</button>
      </div>
    </nav>

    <main class="content">
      <div v-if="aktuelleAnsicht === 'home'">
        <h1>Willkommen im System</h1>
        <p>Wählen Sie oben einen Bereich aus, um zu starten.</p>
      </div>

      <div v-else-if="aktuelleAnsicht === 'liste'">
        <h1>Zimmerliste</h1>
        <button @click="showFilterPanel = true" class ="filter-trigger-btn">Filter</button>

        <div v-if="showFilterPanel" class="panel-overlay">
          <div class="panel-content">
            <h2>Zimmer filtern</h2>
          </div>
          <div class="input-group">
            <div>
              <label>Nach ID filtern:</label>
              <input type="number" v-model="filterId" placeholder="Zimmer ID">
            </div>
              <label>Zimmergröße:</label>
              <select v-model="filterRoomSize">
                <option value =""></option>
                <option value="EINZELZIMMER">Einzelzimmer</option>
                <option value="DOPPELZIMMER">Doppelzimmer</option>
                <option value="SUITE">Suite</option>
              </select>
            <div>
              <label>Minibar</label>
              <select v-model="filterHasMinibar">
                <option value =""></option>
                <option value="true">Minibar</option>
                <option value="false">Keine Minibar</option>
              </select>
            </div>
            <div>
              <label>Verfügbarkeit</label>
              <select v-model="filterIsOccupied">
                <option value =""></option>
                <option value="true">Gebucht</option>
                <option value="false">Frei</option>
              </select>
            </div>
          </div>
          <div class="panel-actions">
            <button @click="filterRooms(filterId)">Filter anwenden</button>
            <button @click="showFilterPanel = false">Filter schließen</button>
          </div>
        </div>
        <table>
          <thead>
            <tr>
              <th>Zimmernummer</th>
              <th>Zimmergröße</th>
              <th>Minibar vorhanden</th>
              <th>Zimmer frei</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="room in rooms" :key="room.id">
              <td>{{ room.roomNumber }}</td>
              <td class="capitalize">{{ room.roomSize }}</td>
              <td>{{ room.hasMinibar ? 'Ja' : 'Nein' }}</td>
              <td>{{ room.isOccupied ? 'Gebucht' : 'Frei' }}</td>
              <td><button @click="deleteRoom(room.roomNumber)">Löschen</button></td>
              <td><button @click="editRoom(room.roomNumber)">Editieren</button></td>
            </tr>
          </tbody>
        </table>
        <div v-if="showEditRoomPanel" class="edit-overlay">
          <div class="edit-content">
            <div>
              <label>Zimmernummer:</label>
              <input type="number" v-model="newRoomNumber" placeholder="1-999">
            </div>
            <div>
              <label>Zimmergröße:</label>
              <select v-model="newRoomSize">
                <option value=""></option>
                <option value="EINZELZIMMER">Einzelzimmer</option>
                <option value="DOPPELZIMMER">Doppelzimmer</option>
                <option value="SUITE">Suite</option>
              </select>
            </div>
            <div>
              <label>Minibar:</label>
              <select v-model="newHasMinibar">
                <option value=""></option>
                <option value="true">Hat Minibar</option>
                <option value="false">Keine Minibar</option>
              </select>
            </div>
            <div>
              <label>Verfügbarkeit:</label>
              <select v-model="newIsOccupied">
                <option value=""></option>
                <option value="true">Belegt</option>
                <option value="false">Verfügbar</option>
              </select>
            </div>
            <div>
              <button @click="editRooms(newRoomNumber)">Änderungen speichern</button>
              <button @click="showEditRoomPanel = false">Abbrechen</button>
            </div>
          </div>
        </div>
        <div class="add-hotel-rooms">
          <button @click="showAddRoomPanel = true" class="addHotel-trigger-btn">Neues Hotelzimmer anlegen</button>

          <div v-if="showAddRoomPanel" class="panel-overlay">
            <div class="panel-content">
              <label>Zimmernummer:</label>
              <input type="number" v-model="newRoomNumber" placeholder="1-999">

              <label>Zimmergröße:</label>
              <select v-model="newRoomSize">
                <option value=""></option>
                <option value="EINZELZIMMER">Einzelzimmer</option>
                <option value="DOPPELZIMMER">Doppelzimmer</option>
                <option value="SUITE">Suite</option>
              </select>

              <label>
                <select v-model="newHasMinibar">
                  <option value=""></option>
                  <option value="true">Hat Minibar</option>
                  <option value="false">Keine Minibar</option>
                </select>
                <input type="checkbox" v-model="newHasMinibar"> Minibar vorhanden?
              </label>
            </div>
            <div>
              <button @click="safeAndAddRoom">Hotelzimmer hinzufügen</button>
            </div>
          </div>
        </div>
      </div>

      <div v-else-if="aktuelleAnsicht === 'verwaltung'">
        <h1>Zimmerverwaltung</h1>
        <p>Hier kannst du Zimmer bearbeiten oder neu anlegen.</p>
      </div>
    </main>
  </div>
</template>

<style>
body {
  margin: 0;
  font-family: sans-serif;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
  background-color: #35495e;
  color: white;
  height: 60px;
  box-sizing: border-box;
  z-index: 1000;
}

.nav-links button {
  margin-left: 10px;
  padding: 8px 15px;
  cursor: pointer;
  background: #42b883;
  border: none;
  color: white;
  border-radius: 4px;
}

.content {
  margin-top: 80px;
  padding: 2rem;
  text-align: center;
}

.capitalize {
  text-transform: lowercase;
  display: inline-block;
}

.capitalize::first-letter {
  text-transform: uppercase;
}
</style>