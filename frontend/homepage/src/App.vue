<template>
  <div class="hotel-manager">
    <nav class="navbar">
      <div class="logo">🏨 eXXellent Nights</div>
      <div class="nav-links">
        <router-link to="/home" class="nav-btn">Homepage</router-link>
        <router-link to="/rooms" class="nav-btn">Zimmerverwaltung</router-link>
      </div>
    </nav>
    <nav class="legal-notes">
      <p>Legal Notes: Wenn ich mit diesem Programm reich werde, spendiere ich Christopher Fullarton ein Bier</p>
    </nav>

    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<style>

.navbar {
  position: fixed; top: 0; left: 0; width: 100%;
  display: flex; justify-content: space-between; align-items: center;
  padding: 0 2rem; background-color: #35495e; color: white; height: 60px;
  z-index: 1000;
}
.nav-btn {
  margin-left: 10px; padding: 8px 15px; color: white;
  text-decoration: none; background: #42b883; border-radius: 4px;
}

.legal-notes {
  position: fixed; bottom: 0; left: 0; width: 100%
}
.content { margin-top: 80px; padding: 2rem; text-align: center; }
</style>