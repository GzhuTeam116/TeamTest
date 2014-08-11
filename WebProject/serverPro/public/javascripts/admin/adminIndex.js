
var adminIndex=angular.module('adminIndex',['ngRoute','ngResource'])
 var HOST="http://"+window.location.host;
adminIndex.controller('adminIndexController',function($scope){
$scope.resourceLists="";
$.ajax({
url:HOST+"/adminGetList",
type:"GET",
dataType:"JSON",
success:function(data){
console.log(data)
$scope.$apply(function(){
$scope.resourceLists=data["resourceList"];
})

},
error:function(data){
}
})
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
