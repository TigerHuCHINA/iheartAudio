<template>
  <div class="shade">
    <div class="log-in">
      <div class="exit-log-in" @click="exitLogIn"></div>
      <div class="row-item row-item-header">
        <div class="logo">iHeartAudio</div>
      </div>
      <div class="row-item">
        <input class="input-box" placeholder="用户名" type="text" v-model="logInUserName">
      </div>
      <div class="row-item">
        <input class="input-box" placeholder="密码" type="password" v-model="logInPassword">
      </div>
      <input type="submit" @click="logIn" class="btn-log-in" value="登录">
      <div class="warn" v-if="showWarn">{{warnMessage}}</div>
      <div class="right-bottom" @click="goToRegister">
        还未注册？
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'log-in',
  data () {
    return {
      show: true,
      showWarn: false,
      logInUserName: '',
      logInPassword: '',
      warnMessage: ''
    }
  },
  methods: {
    exitLogIn () {
      this.$emit('showChange')
    },
    goToRegister () {
      this.$emit('goToRegister')
    },
    logIn () {
      if (this.logInUserName === '' || this.logInPassword === '') {
        this.warnMessage = '账号或密码不能为空'
      } else {
        let param = {
          user_name: this.logInUserName,
          password: this.logInPassword
        }
        axios.get('http://localhost:8080/login', {params: param}).then(res => {
          if (res.data.code === 200) {
            sessionStorage.setItem('user', JSON.stringify(res.data.res))
            this.$emit('loginSuccess')
            this.$emit('showChange')
          } else {
            this.warnMessage = res.data.msg
            this.showWarn = true
            setTimeout(() => {
              this.showWarn = false
            }, 2000)
          }
        })
      }
    }
  }
}
</script>

<style scoped>
  .shade{
    position: absolute;
    top: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(33,33,33,.5);
    z-index: 9;
  }
  .logo{
    background:linear-gradient(-45deg, #EE7752, #E73C7E, #23A6D5, #23D5AB);
    background-size:100% 100%;
    -webkit-background-clip:text;
    -webkit-text-fill-color:transparent;
    -webkit-text-stroke:1px transparent;
  }
  .log-in{
    width: 356px;
    height: 350px;
    border-radius: 2px;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    margin: auto;
    background: white;
    z-index: 10;
  }
  .exit-log-in{
    width: 16px;
    height: 16px;
    position: absolute;
    top: 10px;
    right: 10px;
    background-image: url("https://passport.baidu.com/passApi/img/pass_login_icons_retina.png");
    background-position: -88px -58px;
    cursor: pointer;
  }
  .exit-log-in:hover{
    background-position: -88px -89px;
  }
  .row-item{
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-top: 20px;
    height: 50px;
    align-items: center;
  }
  .row-item-header{
    justify-content: center;
    padding-left: 0;
  }
  .input-box{
    width: 284px;
    height: 20px;
    padding: 8px;
  }
  .btn-log-in{
    margin: 30px auto 0;
    color: white;
    background: rgb(46, 130, 255);
    border: 0;
    border-radius: 3px;
    display: block;
    height: 40px;
    line-height: 40px;
    width: 300px;
    font-size: 16px;
    font-weight: 800;
    cursor: pointer;
    appearance: none;
  }
  .btn-log-in:hover{
    background:rgba(46, 130, 255,.9);
  }
  .warn{
    color: red;
    font-size: 14px;
    margin-top: 10px;
    margin-left: 30px;
  }
  .right-bottom{
    position: absolute;
    right: 30px;
    bottom: 15px;
    color: rgb(46, 130, 255);
    cursor: pointer;
    font-size: 14px;
  }
  .right-bottom:hover{
    color: rgba(46, 130, 255,.5);
  }
</style>
