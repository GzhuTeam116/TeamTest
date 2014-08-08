
var adminIndex=angular.module('adminIndex',['ngRoute','ngResource'])
 var HOST="http://"+window.location.host;
adminIndex.controller('adminIndexController',function($scope){

$scope.loginOut=function(){
$.ajax({
url:HOST+"/userLoginOut",
type:"POST",
success:function(){
window.location.href="index.html";
},
error:function(data){
alert("loginOut fail")
console.log(data)
}
});
}
$scope.addShop=function(){
window.location.href="addResource.html";
}
//$scope.resourceName="abc";
})
