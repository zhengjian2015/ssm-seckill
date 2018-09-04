//存放主要交互逻辑
//javascript 模块化
//seckill.detail.init(param)
var seckill = {
    //封装秒杀相关ajax的url
    URL : {
        now:function() {        
            return "/ssm-seckill/seckill/time/now";
        },
        exposer : function(seckillId){
            return '/ssm-seckill/seckill/'+seckillId+'/exposer';
        },
        execution : function(seckillId,md5){
            return '/ssm-seckill/seckill/'+seckillId+ '/' +md5+'/execution';
        }
    },
    //处理秒杀逻辑
    handleSeckillkill:function(seckillId,node) {
    	node.hide().html('<button class = "btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
    	$.post(seckill.URL.exposer(seckillId),{'seckillId':seckillId},function(result){
    		 //在回调函数中执行交互逻辑
            if(result&&result['success']){
            	var exposer =result['data'];
            	if(exposer['exposed']) {
            		//开启秒杀
                    //获取秒杀地址
                    var md5 =exposer['md5'];
                    var killUrl=seckill.URL.execution(seckillId,md5);
                    console.log('killUrl:'+killUrl);
                    //不用click用one 只绑定一次点击事件
                    $('#killBtn').one('click',function(){
                        //执行秒杀请求的操作
                        //先禁用按钮
                        $(this).addClass('disabled');
                        //发送秒杀请求 
                        console.log('killUrl:'+killUrl);
                        $.post(killUrl,{},function(result){
                            if(result && result['success']){
                                var killResult=result['data'];
                                var state =killResult['state'];
                                var stateInfo=killResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }
                        });
                    });
                    node.show();
            	}else{
                    //未开启秒杀
                    var now =exposer['now'];
                    var start =exposer['start'];
                    var end =exposer['end'];
                    //重新计算及时逻辑
                    seckill.countDown(seckillId,now,start,end);
                    var md5 =exposer['md5'];
                    var killUrl=seckill.URL.execution(seckillId,md5);
                }
            }else{
                console.log('result:'+result);
            }
    		 
    	 });
    },
    //验证手机好
    validatePhone:function(phone) {
        //console.log(phone,phone.length,NaN(phone));
        if(phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    cutdown:function(seckillId,nowTime,startTime,endTime) {
    	var seckillBox = $('#seckill-box');
        //时间的判断
        if(nowTime>endTime) {
            //秒杀结束
        	seckillBox.html("秒杀结束");
        } else if (nowTime<startTime){
            //秒杀未开始.计时时间绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function(event){
                //时间格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //获取秒杀地址，控制实现逻辑，执行秒杀
            }).on('finish.countdown',function(){
            	//获取秒杀地址，控制实现逻辑，执行秒杀
                seckill.handleSeckillkill(seckillId,seckillBox);
            });
        } else {
            //秒杀开始
        	seckill.handleSeckillkill(seckillId,seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init : function(params) {
            //手机验证和登陆，计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie("killPhone");
            console.log("cookie"+killPhone);
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId']
            //验证手机号
            if(!seckill.validatePhone(killPhone)) {
                //绑定phone
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    //显示弹出层
                    show:true,
                    backdrop:'static',//禁止位置关闭
                    keyboard:false,//关闭键盘事件
                });
                $("#killPhoneBtn").click(function(){
                    var inputPhone = $("#killPhoneKey").val();
                    if(seckill.validatePhone(inputPhone)){
                        console.log(seckill.validatePhone(inputPhone));
                        //写入电话 写入cookie
                        $.cookie('killPhone',inputPhone,{expiress:7,path:'/ssm-seckill/seckill'});
                        //刷新页码
                        window.location.reload();
                    } else {
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }
            $.get(seckill.URL.now(),{},function(result){
                if(result && result['success']) {
                    var nowTime = result['data'];
                    //时间判断
                    seckill.cutdown(seckillId,nowTime,startTime,endTime);
                } else {
                    console.log("result"+result);
                }
            })
        }
    }
}
