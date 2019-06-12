<template>
<header class="header">
  <div class="wrap" :style="hasBorder">
    <div class="wrap-logo logo" @click="goToHome" title="首页">iHeartAudio</div>
    <div>
      <div v-if="ifLogIn" class="wrap-btn">
        <div class="wrap-item" title="首页" @click="goToHome">首页</div>
        <div class="wrap-item" title="个人中心">{{user_name}}</div>
        <div class="wrap-item" title="个人中心" @click="goToMyInfo">个人中心</div>
        <div class="wrap-item" title="退出登录" @click="logout">退出登录</div>
      </div>
      <div v-else class="wrap-btn">
        <div class="wrap-item" title="首页" @click="goToHome">首页</div>
        <div class="wrap-item" title="注册" @click="showRegister">注册</div>
        <div class="wrap-item" title="登录" @click="showLogIn">登录</div>
      </div>
    </div>
  </div>
  <div class="header-img" v-if="imgShow" @click="goToHome"></div>
  <log-in v-if="show1" @showChange="exitLogIn" @goToRegister="exitLogIn();showRegister()" @loginSuccess="getUser"></log-in>
  <register v-if="show2" @showChange="exitRegister" @goToLogIn="exitRegister();showLogIn()" @registSuccess="getUser"></register>
</header>
</template>

<script>
import LogIn from '@/components/home/Log-In.vue'
import Register from '@/components/home/register.vue'
export default {
  name: 'Header',
  components: {
    Register,
    LogIn
  },
  data () {
    return {
      show1: false,
      show2: false,
      imgShow: true,
      userFace: '',
      hasBorder: '',
      userId: '',
      user_name: '',
      ifLogIn: false
    }
  },
  methods: {
    goToHome () {
      this.$router.replace('/')
    },
    logout () {
      sessionStorage.clear()
      this.user_name = ''
      this.ifLogIn = false
      this.goToHome()
      this.$emit('logout')
    },
    showLogIn () {
      this.show1 = true
    },
    exitLogIn () {
      this.show1 = false
    },
    showRegister () {
      this.show2 = true
    },
    exitRegister () {
      this.show2 = false
    },
    goToMyInfo () {
      this.$router.push('/myinfo')
    },
    getUser () {
      this.user_name = JSON.parse(sessionStorage.getItem('user')).user_name
      console.log(JSON.parse(sessionStorage.getItem('user')))
      this.ifLogIn = true
      this.$emit('logIn')
    }
  },
  created () {
    if (sessionStorage.getItem('user')) {
      this.ifLogIn = true
      this.user_name = JSON.parse(sessionStorage.getItem('user')).user_name
    }
  }
}
</script>

<style scoped>
  .header{
    width: 100vw;
    height: 170px;
  }
  .wrap{
    display: flex;
    height: 42px;
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    background-color: rgba(255,255,255,.4);
    z-index: 1;
  }
  .logo{
    background-image:linear-gradient(-45deg, #EE7752, #E73C7E, #23A6D5, #23D5AB);
    background-position:100% 100%;
    -webkit-background-clip:text;
    -webkit-text-fill-color:transparent;
    -webkit-text-stroke:1px transparent;
  }
  .header-img{
    position: absolute;
    top: 0;
    background: linear-gradient(-45deg, #EE7752, #E73C7E, #23A6D5, #23D5AB) !important;
    background-size: 400% 400%;
    animation: gradient 15s ease infinite;
    width: 100vw;
    height: 170px;
    cursor: pointer;
    z-index: -1;
  }
  @keyframes gradient{
    0%{
      background-position: 0 50%;
    }
    50%{
      background-position: 100% 50%;
    }
    100%{
      background-position: 0 50%;
    }
  }
  .wrap-logo{
    font-size: 30px;
    height: 42px;
    cursor: pointer;
    display: flex;
    align-items: center;
  }
  .wrap-item:hover{
    background: rgba(255,255,255,.4);
  }
  .wrap-btn{
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }
  .wrap-item{
    padding:0 auto;
    cursor: pointer;
    width: 50px;
    height: 42px;
    color: #222;
    font-size: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>
