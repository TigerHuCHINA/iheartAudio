<template>
  <div>
    <ul v-for="(line,index) of lines" :key="index">
      <li v-for="item of line" :key="item.productId">
        <div @click="handleClick(item.productId)" class="item border-bottom" >
          <div class="item-content" :title="item.productName">
            <img class="item-img" :src="item.imgUrl"/>
            <p class="item-title">{{item.productName}}</p>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Recommend',
  props: ['list'],
  data () {
    return {
      user_name: ''
    }
  },
  computed: {
    lines () {
      const lines = []
      this.list.forEach((item, index) => {
        const line = Math.floor(index / 4)
        if (!lines[line]) {
          lines[line] = []
        }
        lines[line].push(item)
      })
      return lines
    }
  },
  methods: {
    handleClick (id) {
      if (this.user_name === '') {
        alert('请先登录')
      } else {
        this.$router.push('/productDetail/' + id)
      }
    }
  },
  created () {
    var user = sessionStorage.getItem('user')
    console.log(user)
    if (user) {
      this.user_name = JSON.parse(user).user_name
    }
    this.$forceUpdate()
  }
}
</script>

<style scoped>
  .item-content {
    margin-left: 30px;
    margin-top: 10px;
    width: 100%;
    text-align: center;
  }
  .item-img{
    margin: 0 auto;
    width: 120px;
    height: 120px;
    padding: 1px;
  }
  .item-title {
    margin-top: 5px;
    font-size: 10px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  ul {
    list-style: none;
    display: flex;
    width: 80%;
    margin: 0 auto;
  }
  .item {
    color:black;
    text-decoration: none;
    cursor: pointer;
  }
  .item:hover{
    color: rgb(46, 130, 255);
    opacity: .9;
  }

</style>
