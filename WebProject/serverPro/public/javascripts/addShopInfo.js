 var HOST="http://"+window.location.host;
function addShopInfo(){
$.ajax({
url:HOST+"/addShopInfo",
type:"POST",
success:function(data){
alert("success"+data)
},
error:function(data){
alert("error"+data)
}
});
$("#loginOut").live('click',function(){
alert("click")
$.ajax({
url:HOST+"/userLoginOut",
type:"POST",
success:function(){
window.location.href="index.html";
},
error:function(data){
console.log(data)
}
});
})
}
addShopInfo();