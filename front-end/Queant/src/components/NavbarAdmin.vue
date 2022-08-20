<template>
  <header class="header">
    <h1 class="logo">
      <router-link
        :to="{ name: 'home' }">
        <img class="logo mr-4" src="@/assets/image/퀸트_로고.png" alt="logo image">
      </router-link>
    </h1>

    <ul class="main-nav">
      <li v-show="isAdmin">
        <router-link :to="{ name: 'admin' }">Manage</router-link>
      </li>

      <li v-show="isAdmin">
        <router-link :to="{ name: 'adminSpecial' }">Report</router-link>
      </li>
    </ul>
	</header> 
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'NavbarAdmin',
  computed: {
    ...mapGetters(['isAdmin'])
  }
}
</script>

<style scoped>

* {
  z-index: 8;
	box-sizing: border-box;
}
body {
	font-family: 'Montserrat', sans-serif;
	line-height: 1.6;
	margin: 0;
	min-height: 100vh;
}
ul {
  margin: 0;
  padding: 0;
  list-style: none;
}


h2,
h3,
a {
	color: #34495e;
}

a {
	text-decoration: none;
}



.logo {
	margin: 0;
	font-size: 1.45em;
}

.main-nav {
	margin-top: 5px;

}
.logo a,
.main-nav a {
	padding: 10px 15px;
	text-transform: uppercase;
	text-align: center;
	display: block;
}

.main-nav a {
	color: #34495e;
	font-size: .99em;
}

.main-nav a:hover {
	color: #718daa;
}



.header {
	padding-top: .5em;
	padding-bottom: .5em;
	border: 1px solid #a2a2a2;
	background-color: #f4f4f4;
	-webkit-box-shadow: 0px 0px 14px 0px rgba(0,0,0,0.75);
	-moz-box-shadow: 0px 0px 14px 0px rgba(0,0,0,0.75);
	box-shadow: 0px 0px 14px 0px rgba(0,0,0,0.75);
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
}


/* ================================= 
  Media Queries
==================================== */
input {
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  outline: 0;
  border: 1px solid darkgray;
  background-color: white;
  width: 250px;
  border-radius: 3px;
  display: block;
  text-align: center;
  font-size: 18px;
  color: black;
  -webkit-transition-duration: 0.25s;
          transition-duration: 0.25s;
  font-weight: 300;
}
input:hover {
  background-color: rgba(255, 255, 255, 0.4);
}
input:focus {
  background-color: white;
  width: 300px;
  color: #34495e;
}



@media (min-width: 769px) {
	.header,
	.main-nav {
		display: flex;
	}
	.header {
    top: 0;
    position: fixed;
		flex-direction: column;
		align-items: center;
  }
  .header{
		width: 100%;
		margin: 0 auto;
		/* max-width: 1150px; */
	}

}

@media (min-width: 1025px) {
	.header {
		flex-direction: row;
		justify-content: space-between;
	}

}

</style>