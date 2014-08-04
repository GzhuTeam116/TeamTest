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
}
addShopInfo();