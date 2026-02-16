<script setup>
import {ref, onMounted} from "vue";

const numFreeRooms = ref('');
onMounted(() => {
  getNumFreeRooms();
  }
)

const getNumFreeRooms = () => {
  fetch('http://localhost:8080/rest/rooms/home')
      .then(response => response.json())
      .then(data => {
        console.log("Backend data:", data);
        numFreeRooms.value = data;
      })
      .catch(error => console.error("Fehler:", error));
}
</script>

<template>
  <div class="home-view">
    <div class="welcome">
      <h1>Willkommen bei Hotel eXXellent!</h1>
    </div>
    <div class="cutomer-information">
      <h2>Aktuell haben wir {{ numFreeRooms }} freie Zimmer</h2>
    </div>
  </div>
</template>

<style scoped>
.home-view {
  animation: fadeIn 0.5s ease-in;

  width: 100vw;
  position: relative;
  left: 50%;
  right: 50%;
  margin-left: -50vw;
  margin-right: -50vw;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.stat-card {
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  border-left: 5px solid #42b883;
  display: inline-block;
  margin-top: 20px;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>