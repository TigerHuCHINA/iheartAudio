<template>
  <div class="shade">
    <div class="register">
      <div class="exit-register" @click="exitRegister"></div>
      <div class="row-item row-item-header">
        <div class="logo">iHeartAudio</div>
      </div>
      <div class="row-item">
        <input class="input-box" placeholder="用户名" type="text" v-model="registerUserName">
      </div>
      <div class="row-item">
        <input class="input-box" placeholder="密码" type="password" v-model="registerPassword">
      </div>
      <div class="row-item">
        <input class="input-box" placeholder="确认密码" type="password" v-model="registerConfirmPassword">
      </div>
      <input type="submit" @click="regist" class="btn-log-in" value="注册">
      <div class="warn" v-if="showWarn">{{warnMessage}}</div>
      <div class="right-bottom" @click="goToLogIn">
        已有账号，现在登录
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Register',
  data () {
    return {
      show: true,
      showWarn: false,
      registerUserName: '',
      userNameUsable: true,
      registerPassword: '',
      registerConfirmPassword: '',
      warnMessage: '',
      userID: 0
    }
  },
  methods: {
    exitRegister () {
      this.$emit('showChange')
    },
    goToLogIn () {
      this.$emit('goToLogIn')
    },
    checkPassword () {
      if (this.registerPassword && this.registerConfirmPassword) {
        if (this.registerPassword === this.registerConfirmPassword) {
          return true
        } else {
          this.warnMessage = '两次输入密码不一致'
          this.showWarn = true
          setTimeout(() => {
            this.showWarn = false
          }, 2000)
          return false
        }
      } else {
        this.warnMessage = '密码不能为空'
        this.showWarn = true
        setTimeout(() => {
          this.showWarn = false
        }, 2000)
        return false
      }
    },
    checkUserName () {
      if (this.username !== null) {
        return true
      }
    },
    regist () {
      if (this.checkPassword() && this.checkUserName()) {
        let param = {
          id: this.userID,
          user_name: this.registerUserName,
          password: this.registerPassword
        }
        axios.get('http://localhost:8080/register', {params: param}).then(res => {
          if (res.data.code === 200) {
            sessionStorage.setItem('user', JSON.stringify(res.data.result))
            this.$emit('registSuccess')
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
  .register{
    width: 356px;
    height: 415px;
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
  .exit-register{
    width: 16px;
    height: 16px;
    position: absolute;
    top: 10px;
    right: 10px;
    background-image: url("https://passport.baidu.com/passApi/img/pass_login_icons_retina.png");
    background-position: -88px -58px;
    cursor: pointer;
  }
  .exit-register:hover{
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
