var addResource=angular.module('addResource',['ngRoute','ngResource'])
var HOST="http://"+window.location.host;

addResource.controller('addRecourseController',function($scope){
$scope.speciesNames="";//get select of speciesNames
$scope.shelfNames="";//get select of shelfNames
//------------------------------------resource msg---------------------------
$scope.picUrl="";//upload pic url
$scope.selectedSpecies="";//selectedSpecies
$scope.selectedShelf="";//selectedShelf
$scope.bookName="";
$scope.bookPrice="";
$scope.bookPublish="";
$scope.bookNum="";
$scope.bookISBN="";
$scope.bookAuthor="";
//---------------------------------------------------------------------
$scope .addResource=function(){
var options={
   target:'#id_iframe',
   url:HOST+"/upLoad",
   type:'POST',
   success:function(data,statusText){
   if(data["code"]==0){
   $scope.picUrl=data["url"]
   }
     alert(data["msg"])
   },

};
   $('#uploadFromId').ajaxForm(options).submit(function(){
      return false;
   });

}
$scope.addResource();
$.ajax({
url:HOST+"/addResourceInfo",
type:"GET",
dataType:"JSON",
success:function(data){
  console.log(data["speciesNames"][0].name)
  console.log(data["shelfNames"][0].name)
  $scope.$apply(function(){
    $scope.speciesNames=data["speciesNames"];
      $scope.shelfNames=data["shelfNames"];
  });


},
error: function(data){
console.log(data)
}
});

$scope.mackSureAdd=function(){
 if(!$scope.bookName){
 alert("请填写书名")
 return false;
 }
 if(!$scope.bookPrice){
  alert("请填写价格")
  return false;
  }
 if(!$scope.bookPublish){
   alert("请填写出版社")
   return false;
   }
 if(!$scope.bookNum){
    alert("请填写数量")
    return false;
    }
 if(!$scope.bookISBN){
    alert("请填写ISBN")
    return false
    }
 if(!$scope.bookAuthor){
   alert("请填写作者")
     return false
 }
 if(!$scope.picUrl){
    alert("请上传图片")
    return false
    }
 if(!$scope.selectedSpecies){
    alert("请选择图书的种类")
    return false
    }
 if(!$scope.selectedShelf){
    alert("请选择书架")
    return false
    }

var params={
"bookName":$scope.bookName,
"bookPrice":$scope.bookPrice,
"bookPublish":$scope.bookPublish,
"bookNum":$scope.bookNum,
"bookISBN":$scope.bookISBN,
"bookAuthor":$scope.bookAuthor,
"picUrl":$scope.picUrl,
"selectedSpecies":$scope.selectedSpecies.id,
"selectedShelf":$scope.selectedShelf.id
}
console.log(params)
 $.ajax({
  url:HOST+"/adminAddShop",
  type:"POST",
  data:params,
  success:function(data){
    console.log(data)
    if(data=="success"){
    window.location.href="adminIndex.html"
    }
  },
  error:function(data){
  console.log(data)
  }
 })
}

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
});

var imgurl = "";
function getImgURL(node) {
	var imgURL = "";
    try{
        var file = null;
        if(node.files && node.files[0] ){
        	file = node.files[0];
        }else if(node.files && node.files.item(0)) {
        	file = node.files.item(0);
        }
        //Firefox 因安全性问题已无法直接通过input[file].value 获取完整的文件路径
        try{
        	//Firefox7.0
			imgURL =  file.getAsDataURL();
			//alert("//Firefox7.0"+imgRUL);
        }catch(e){
        	//Firefox8.0以上
        	imgRUL = window.URL.createObjectURL(file);
			//alert("//Firefox8.0以上"+imgRUL);
        }
     }catch(e){      //这里不知道怎么处理了，如果是遨游的话会报这个异常
        //支持html5的浏览器,比如高版本的firefox、chrome、ie10
        if (node.files && node.files[0]) {
        	var reader = new FileReader();
        	reader.onload = function (e) {
        	    imgURL = e.target.result;
        	};
        	reader.readAsDataURL(node.files[0]);
        }
     }
	//imgurl = imgURL;
	creatImg(imgRUL);
	return imgURL;
}

function creatImg(imgRUL){   //根据指定URL创建一个Img对象
	var textHtml = "<img src='"+imgRUL+"'/>";
	$(".mark").after(textHtml);
}




