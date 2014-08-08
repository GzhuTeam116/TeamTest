 var HOST="http://"+window.location.host;
function login(){
  $("#loginBut").live('click',function(){
   var login_username = $("#username").val();
   var login_password=$("#password").val();
   if(!login_username||!login_password){
     alert("请填入账号或密码")
     return
   }
      $.ajax({
            url:  HOST + "/login",
            type: "POST",
            dataType:"JSON",
            data:{ "userName":login_username,"password":login_password},
            success: function(data){
            if(data["code"]==0){
              window.location.href="adminIndex.html"
            }else{
               alert(data["msg"])
            }
                console.log(data);
            },
            error: function(data){
            alert("网络故障，请重新操作")
                console.log(data);
            }
        });
  })
  var get_cookie=getCookie("userId");
 if (get_cookie!=""){
  window.location.href="adminIndex.html"
 }

}
login()
function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
    {
    c_start=c_start + c_name.length+1;
    c_end=document.cookie.indexOf(";",c_start);
    if (c_end==-1) c_end=document.cookie.length;
    var tempResult=unescape(document.cookie.substring(c_start,c_end));
    var len=tempResult.length
   tempResult=tempResult.substring(0, len-1)
    return  tempResult
    }
  }
return ""
}

