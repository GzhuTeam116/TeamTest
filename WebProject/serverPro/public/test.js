/**
 * Created by Administrator on 2014/8/18.
 */
function test(){
alert("a")
    $.ajax({
       url:"http://127.0.0.1:9000/GetAddCartResult",
       type:"POST",
       dateType:"JSON",
       data:{"book_id":"31","book_account":"4"},
        success:function(date){
            console.log(date)
        },
       error:function(data){
           console.log(data);
       }
    })
}
test();