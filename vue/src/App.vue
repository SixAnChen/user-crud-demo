<template>
  <div id="app">
    <h1>Hello Vue!</h1>
    <p>当前时间：{{ currentTime }}</p>
    <router-view/>
  </div>
</template>

<style>
h1, p {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
<script>
import request from '@/utils/request'

export default {
  data() {
    return {
      currentTime: new Date().toLocaleTimeString(),
    }
  },
  created() {
    // 使用 setInterval 更新时间
    this.updateTime();
  },
  methods: {
    updateTime() {
      setInterval(() => {
        this.currentTime = new Date().toLocaleTimeString();
      }, 1000); // 1000毫秒更新一次
    },
  }
}
</script>
