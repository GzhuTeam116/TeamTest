function login(){
  $("#loginBut").live('click',function(){
   var login_username = $("#username").val();
   var login_password=$("#password").val();
     var host="http://"+window.location.host;

      $.ajax({
            url:  host + "/login",
            type: "POST",
            data:{ "userName":login_username,"password":login_password},
            success: function(data){
//             alert(data)
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
  })

}
login()
