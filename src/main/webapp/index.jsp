<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/inc/header.jsp"%>
<head>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>分润管理系统</title>
<link href="${pageContext.request.contextPath}/static/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
<!-- 自定义js -->
<script src="${pageContext.request.contextPath}/static/js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/contabs.js"></script>

<!-- 第三方插件 -->
<script src="${pageContext.request.contextPath}/static/js/plugins/pace/pace.min.js"></script>

<!-- Bootstrap-Treeview plugin javascript -->
<script src="${pageContext.request.contextPath}/static/js/plugins/treeview/bootstrap-treeview.js"></script>



<style>
.user-circle {
	width: 30px;
	height: 30px;
	background: #18a689;
	border-radius: 50%;
	display: inline-block;
	color: #fff;
	vertical-align: middle;
	font-size: 14px;
	text-align: center;
	line-height: 30px;
	margin-left: 10px;
}

#right-set.navbar-top-links>li>a {
	padding: 16px 10px;
}

.fixed-nav #content-main {
	height: calc(100% -   43px);
	overflow: hidden;
}
.menu-icon{
	width:12px;
	height:12px;
	vertical-align:middle;
}

/*提示框*/
        .reset-pwd{
            margin: 20px 15px 0px 20px;
            font-size: 14px;
        }
        .reset-pwd div{
            margin-bottom: 15px;
        }
        .resetpwd-input{
            padding: 4px;
            width: 250px;
            border: 1px solid #eee;
        }
        
       .dialog-content{
       		margin:20px;
       		line-height:2;
       }
       .dialog-input{
       		text-align:center;
       }
       .dialog-input div{
       		margin:15px 0px;
       }
       
.telcode-btn {
	border: none;
	background-color: #fff;
	font-size: 12px;
	height: 30px;
	width: 100px;
	color: #1a77b8;
	outline: none;
}
.user-info{
	display:inline-block;
}
.user-info input{
	padding: 4px;
    width: 220px;
    border: 1px solid #eee;
}
.text-margin{
	margin-bottom:10px;
}
</style>
	 <script>
	 
		var wait = 60;
		var phoneRule = "^[1][3-8]\\d{9}$";
		
		//跳转到  选择代理商账户 页面
		function choseAgent() {
			
			if('${loginType}' == "AccountPwd"){
				layerAlert("当前只有一个代理帐号，无需切换");
				return false;
    		};
			
			$.post('${http}'+"/platform/platform.do?method=destroySession",function(data) {
				if (data.code == "0000") {
					window.location.href = "${pageContext.request.contextPath}/choseAgent";
				}
				else{
					layerAlert(data.msg);
				}
			}, "json");
		}
		
		function mainRefresh() {
		   window.location.reload();
		}

		//账户退出，彻底退出
		function logout() {
			$.post('${http}'+"/platform/platform.do?method=destroySession",function(data) {
				if (data.code == "0000") {
					window.location.href = "${pageContext.request.contextPath}/logout";
				}
				else{
					layerAlert(data.msg);
				}
			}, "json");
		}
		
		//发送手机短信验证码
		function sendMsm(val) {
			var phone = $("#send_phone").val();
			if (phone.match(phoneRule) == null || phone.length != 11) {
				layerAlert("请输入正确的手机号");
				return false;
			} 
			settime(val);
			$.post('${http}'+"/users/users.do?method=sendMsg", {
				mobile : phone
			}, function(data) {
				if (data.success == "true") {
					layerAlert("短信发送成功");
					$('#send_code').removeAttr("disabled"); 
				} else {
					layerAlert("短信发送失败");
				}
			}, "json");
		}
		
		//验证码倒计时
		function settime(val) {
			if (wait == 0) {
				val.removeAttribute("disabled");
				val.value = "获取验证码";
				wait = 60;
			} else {
				val.setAttribute("disabled", true);
				val.value = "重新发送(" + wait + ")";
				wait--;
				setTimeout(function() {
					settime(val);
				}, 1000);
			}
		}
		
		/**
		 *  展现实名认证页面
		 **/
		function show_verify(phone) {

			//var phone = $("#send_phone").val();
			
			var htmlContent = '<div class="dialog-content">\
				<form role="form" action="index.html">\
				<div class="text-margin">☆ 请填写您本人或公司的真实信息，以免影响您的分润结算。</div>\
				<div class="form-group">\
					<div class="user-info"><span style="margin-left:20px;">注册手机号：</span><input class="login-input" type="text" placeholder="" id="mobile_hidden" disabled="true" value="'+phone+'" ></div>\
					<div class="user-info"><span style="margin-left:20px;">法人姓名：</span><input class="login-input" type="text" placeholder=""  name="name" id="name" required=""></div>\
				</div>\
				<div class="form-group">\
					<div class="user-info"><span style="margin-left:20px;">注册证件号：</span><input class="login-input" type="text" placeholder="" name="Pid" id="Pid" maxlength="18" required=""></div>\
					<div class="user-info"><span style="margin-left:20px;">公司名称：</span><input class="login-input" type="text"  placeholder="" name="company" id="company"></div>\
				</div>\
				<div class="form-group">\
					<div class="user-info"><span style="margin-left:20px;">营业执照号：</span><input class="login-input" type="text" name="lience" id="lience" placeholder=""></div>\
				</div>\
				<div class="text-margin">☆ 请填写您本人或公司法人的银行账号，以免影响您的分润结算。</div>\
				<div class="form-group">\
					<div class="user-info"><span style="margin-left:5px;">结算账号类型：</span>\
					<div class="input-group" style="width:220px;display:inline-block;">\
	                <select data-placeholder="选择账号类型" class="chosen-select"  style="width:220px;" id="acc_type" onchange="getUnit(this.value)" tabindex="2">\
	                <option value="">请选择账号类型</option>\
	                <option value="01" hassubinfo="true">私户</option>\
	                <option value="02" hassubinfo="true">商户</option>\
	            </select>\
	        </div></div>\
					<div class="user-info"><span style="margin-left:20px;">银行账号：</span><input class="login-input" type="text" placeholder="" name="account_no" id="account_no"></div>\
				</div>\
				\
				<div class="form-group" id="bank_name_div">\
				<div class="user-info"><span style="margin-left:5px;">结算卡开户行：</span>\
				<div class="input-group" style="width:220px;display:inline-block;">\
                <select data-placeholder="选择账号开户行" id="bank_name" class="chosen-select"  style="width:220px;"   tabindex="3">\
                <option value="">请选择开户行</option>\
            </select>\
        	</div></div>\
			</div>\
			</form>\
		</div>';
		
		//完善实名认证信息窗口
		  	layer.open({
				type: 1,
				  title: '完善信息',
				  shadeClose: false,
				  closeBtn: 1,//0代表不显示关闭按钮，1（默认）和2代表显示关闭按钮，风格不同
				  shade: 0.3,
				  scrollbar: false,
		          btn: ['提交'],
				  area: ['700px', '520px'],
				  content: htmlContent,
				  yes: function(index) {

				  //提交信息
						var phone = $("#mobile_hidden").val();
						var name = $("#name").val();
						var pId = $("#Pid").val();
						var lience = $("#lience").val();
						var company = $("#company").val();
						 
						var accountNo = $("#account_no").val();
						 
						var accType = $("#acc_type").val();
						var bankName = $("#bank_name").val();
			 
						var bankNo ="";
						var cardType = "01";
						if (name == "") {
							layerAlert("姓名不能为空");
							return false;
						}
						if (pId == "" || pId.length != 18) {
							layerAlert("请填写正确的身份证号");
							return false;
						}
						 
						if (accountNo == "") {
							layerAlert("请填写正确银行卡号");
							return false;
						}
						
						if (accType == "") {
							layerAlert("请选择正确账户类型 ");
							return false;
						}
						if (accType == "02") {
							if (bankName == "") {
								layerAlert("请选择正确开户行");
								return false;
							}else{
								var arr = bankName.split('***');
								bankName = arr[0];
								bankNo = arr[1];
							}
							 
						} else {
						
							$.post('${http}'+"/users/users.do?method=checkAccount", {
							accountNo : accountNo,
							}, function(data) {
							if (data.success == "false") {
								layerAlert("请输入正确的银行卡号（借记卡、储蓄卡）");
								return false;
								} 
							}, "json");
						
							bankName="";
							bankNo="";
							 
						}
						
						//$("#verify_but").hide();
						$.post('${http}'+"/users/users.do?method=verify", {
									mobile : phone,
									name : name,
									Pid : pId,
									lience : lience,
									company : company,
									accountNo : accountNo,
									accType : accType,
									bankName : bankName,
									bankNo : bankNo,
									cardType : cardType
								}, function(data) {
									if (data.success == "true") {
										layerAlert(data.message);
										layer.close(index);
									} else {
										layerAlert(data.message);
										//$("#verify_but").show();
										return false;
									}
								}, "json");
				  },
				  success: function(layero, index){
					  
					    //console.log(layero, index);
					    
					    //查看是否已经实名认证过
					    $.post('${http}'+"/users/users.do?method=checkBindInfo", {
							mobile : phone,
						}, function(data) {
							openVerifyView(data);
						}, "json");

					 	//银行名称列表 下拉列表
					  	getBankInfo();
					  	//$('.chosen-select').chosen();
					  }
				});
				$(".layui-layer-setwin").hide();
		}
		
		
		/**
		 *  完善用户信息 页面
		 **/
		function openVerifyView(data) {
			if(data != null) {
				$("#name").val(data.NAME);
				$("#Pid").val(data.PID);
				$("#lience").val(data.LIENCE);
				$("#company").val(data.COMPANY);
				$("#mobile_hidden").val(data.MOBILE_HIDDEN);
				
				$("#name").attr("disabled", true); 
				$("#Pid").attr("disabled", true);
				$("#lience").attr("disabled", true);
				$("#company").attr("disabled", true);
				$("#mobile_hidden").attr("disabled", true);
			}
		}
		
		//根据选择账户类型，展现不同菜单
		function getUnit(value) {
			
			if(value == "02") {
				$("#bank_name_div").show();
			} else {
				$("#bank_name_div").hide();
			}
		}
		
		//银行名称列表 下拉列表
		function getBankInfo() {
			
			$.post('${http}'+"/users/users.do?method=getBankInfo",
			function(data) {
				if (data.length > 0) {
					for ( var i = 0; i < data.length; i++) {
						 $("#bank_name").append("<option value='"+data[i].BANK_SHORT+"***"+data[i].BANKNO+"'>"+data[i].BANK_SHORT+"</option>"); 
					}
				}
				
				$("#bank_name").trigger("chosen:updated");
			}, "json");
		}

		
		/**
		 *  展现手机号窗口
		 **/
		function showDiv(){
			
			var phoneContent = '<div class="dialog-content"><div>为了更好的为我司代理商提供服务，需要你完善实名信息，请输入手机号，手机号验证后将不允许修改。</div>\
								<div>请确保使用您本人或公司的手机号。</div><div>多个手刷平台后续将通过手机号做合并登录。</div><div>请务必填写自己真实的手机号。</div>\
								<div class="dialog-input">\
								<div>手机号：<input class="resetpwd-input" type="tel" id="send_phone" /></div>\
								<div><span style="margin-left:100px;">验证码：</span>\
								<input class="resetpwd-input" type="text" id="send_code" disabled="disabled"  />\
								<input class="telcode-btn" type="button" id="send_btn" value="获取验证码" onclick="sendMsm(this)" maxlength="11" />\
								</div>\
								</div>\
								</div>';
								
			layer.open({
						type: 1,
						  title: '手机认证',
						  shadeClose: false,
						  closeBtn: 0,//0代表不显示关闭按钮，1（默认）和2代表显示关闭按钮，风格不同
						  shade: 0.3,
						  scrollbar: false,
				          btn: ['提交'],
						  area: ['700px', '420px'],
						  content:  phoneContent,
						  yes: function(index) {
							  /** 
							  var phone = $("#send_phone").val();
							  layer.close(index);
							  show_verify(phone);
								**/
							 
							  	var phone = $("#send_phone").val();
								var sendCode = $("#send_code").val();
								
								if (phone == "" || phone.length != 11) {
									layerAlert("请填写正确的手机号");
									return false;
								}
								if (sendCode == "" || sendCode.length != 4) {
									layerAlert("请填写正确的验证码");
									return false;
								}
								
								$.post('${http}'+"/users/users.do?method=checkCode", {
									mobile : phone,
									code : sendCode
								}, function(data) {
									if (data.success == "true") {
										layer.close(index);
										show_verify(phone);
						  			}
								});
							
				}
			});
		}
		

		/**
		 *  查看是否有实名认证信息
		 **/
		function smrz() {
			
			$.post('${http}'+"/users/users.do?method=getSMRZInfo", {
					mobile : ''
				}, function(data) {
					show_verify(data.MOBILE_HIDDEN);
					if(data==null){
						layerAlert("服务繁忙，请重新尝试。");
					}else
						//展现实名认证窗口
					    showVerifyView(data);
						$(".layui-layer-btn").hide();
						$(".layui-layer-setwin").show();
				}, "json");
		}
		
		
		/**
		 *  展现用户信息页面
		 **/
		function showVerifyView(data) {
			if(data != null) {
				$("#name").val(data.NAME);
				$("#Pid").val(data.PID);
				$("#lience").val(data.LIENCE);
				$("#company").val(data.COMPANY);
				$("#mobile_hidden").val(data.MOBILE_HIDDEN);
				
				//账户信息
				$("#account_no").val(data.ACCOUNT_NO);
				 
				$("#acc_type").val(data.ACC_TYPE);
				$("#bank_name").val(data.BANK_NAME);
				$("#bank_no").val(data.BANK_NO);
				$("#card_type").val(data.CARD_TYPE);

				if(data.ACC_TYPE == "02") {
					$("#bank_name_div").show();
					$("#bank_name").append("<option selected>"+data.BANK_NAME+"</option>");
					$("#bank_name").trigger("chosen:updated");
				}else
					$("#bank_name_div").hide();
				
				$("#name").attr("disabled", true); 
				$("#Pid").attr("disabled", true);
				$("#lience").attr("disabled", true);
				$("#company").attr("disabled", true);
				
				$("#account_no").attr("disabled", true);
	 
				$("#acc_type").attr("disabled", true);
				$("#bank_name").attr("disabled", true);
				$("#bank_no").attr("disabled", true);
				$("#card_type").attr("disabled", true);
			 
				$("#verify_but").hide();
				$("#verify_but_close").show();
				$("#maybe_nextTime1").hide();
				
				
			}
		}
		
		
		
	/**
	 *  启动运行
	**/
	$(function () {
		
			var userName = $(".user-name").text();
			$(".user-circle").text(userName.substr(0, 1));
			

			$.post('${http}'+"/platform/platform.do?method=check4Agent", {
				agencyId : '${agencyId}',
				mobile : '${mobile}',
				sessionId : '${sessionId}'
			}, function(data) {
				if (data.code == "0000") {
					$.post('${http}'+"/platform/platform.do?method=getMenuTree", {
						agencyId : '${agencyId}'
					}, function(data) {
							menuList(data);
							
							$("#iframe0").attr("src","${http}/announce.jsp");
							if('${loginType}'=='AccountPwd'){
								//检查是否已经实名认证过，否-进入实名认证页面。
								$.post('${http}'+"/users/users.do?method=checkBind", function(data) {
									if (data.success == "false") {
										showDiv();
									}else{
										//showDiv();
										//console.log('已经实名认证过');
									}
								}, "json");
							};
						});
				} else{
					layerAlert(data.msg);
				};
			}, "json");
			
			
			
		//菜单展现
		function menuList(menuJsons){
            var menuJson = menuJsons;
			if(menuJson){
                var menuHtml = "";
                for(var i=0; i< menuJson.length;i++){
                    menuHtml += '<li>\
                            <a href="#">\
                            <img class="menu-icon" src="'+menuJson[i].icon+'" />\
                            <span style="vertical-align:middle;" class="nav-label">'+menuJson[i].text+'</span>\
                            <span class="fa arrow"></span>\
                        	</a>\
                        	<ul class="nav nav-second-level">';
		                    for(var j=0; j< menuJson[i].nodes.length;j++){
		                        menuHtml += '<li><a class="J_menuItem" href="${http}/' + menuJson[i].nodes[j].url + '" data-index="'+j+'">'+menuJson[i].nodes[j].text + '</a></li>';
		                    }
                    menuHtml += '</ul></li>';
                }
                $("#side-menu").append(menuHtml);
            }
			
			$('#side-menu').metisMenu();
		}
		
		
		$(function () {
            $("#pass_reset").click(function(){
            	var param = {};
            	if("PhoneSms" == '${loginType}') {
            	param.mobile = '${mobile}';
		       	$.ajax({
					url : basePath + "/user/password/exist",
					type : "POST",
					contentType : "application/json",
					data : JSON.stringify(param),
					dataType : "json",
					success : function(data) {
						if (data.code == "0000") {
							var content="";
							 if(data.result.status == "null") {
							 	content = '<div class="reset-pwd"><div>新&nbsp;&nbsp;密&nbsp;&nbsp;码：<input id="newPwd" class="resetpwd-input" type="password" /></div><div>确认密码：<input id="newPwd1" class="resetpwd-input" type="password" /></div></div>';
							 	resetWindown(content, data);
							 } else {
							 	content = '<div class="reset-pwd"><div>原&nbsp;&nbsp;密&nbsp;&nbsp;码：<input id="oldPwd" class="resetpwd-input" type="password"/></div><div>新&nbsp;&nbsp;密&nbsp;&nbsp;码：<input id="newPwd" class="resetpwd-input" type="password" /></div><div>确认密码：<input id="newPwd1" class="resetpwd-input" type="password" /></div></div>';
							 	resetWindown(content, "");
							 }
						}
					},
				});
			} else {
				var content = '<div class="reset-pwd"><div>原&nbsp;&nbsp;密&nbsp;&nbsp;码：<input id="oldPwd" class="resetpwd-input" type="password"/></div><div>新&nbsp;&nbsp;密&nbsp;&nbsp;码：<input id="newPwd" class="resetpwd-input" type="password" /></div><div>确认密码：<input id="newPwd1" class="resetpwd-input" type="password" /></div></div>';
				resetWindown(content, "");
			}
		});
	});
		
	function resetWindown(content, status) {
		layer.open({
           type: 1,
           title: "重置密码",//title:false 不显示标题栏
           area: ['360px', '250px'],
           content: content,
           btn: ['确定', '取消'],
           btnAlign: 'c', //按钮居中
           shade: 0, //不显示遮罩
           yes: function(index, layero) {
				var newPwd = hex_md5($("#newPwd").val());
				var newPwd1 = hex_md5($("#newPwd1").val());
				if(newPwd != newPwd1) {
					layerAlert("您输入的两次密码，不一致请重新输入");
				} else {
					var param = {};
					if(status == "") {
						param.oldPwd = hex_md5($("#oldPwd").val());
					}
					param.newPwd = newPwd;
					param.httpUrl = '${http}';
			        $.ajax({
						url : basePath + "/user/reset/password",
						type : "POST",
						contentType : "application/json",
						data : JSON.stringify(param),
						dataType : "json",
						success : function(data) {
							console.log(data);
							if (data.code == "0000") {
								 layer.close(index);
								 layerAlert("密码修改成功！");
							} else if (data.code == "3000") {
								layerAlert(data.msg);
							} else {
								wait = 0;
								layerAlert(data.msg);
							}
						},
					});
				}
			}
		});
	};
		});
	</script>

</head>

<body class="fixed-sidebar full-height-layout gray-bg fixed-nav">
	<div id="wrapper" style="overflow:hidden;">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<div style="text-align:right; margin:10px 17px 10px 0px;">
					<a style="width:38px;height:34px;" class="navbar-minimalize btn btn-primary" href="#">
						<i class="fa fa-bars"></i> 
					</a>
				</div>
				<ul class="nav" id="side-menu">
                    
                </ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style=" box-shadow:1px 3px 11px rgba(0,0,0,.2);">
					<span><img style="height:45px; margin:6px 0px 0px 20px;"
						alt="image"
						src="${pageContext.request.contextPath}/static/img/logo-1.png" /></span>
					<ul id="right-set" class="nav navbar-top-links navbar-right">
						<!--  <li><a class="count-info" href="#"> <i class="fa fa-bell"></i>
								<span class="label label-primary" id="announceNum">9</span>
						</a></li>
						<li><a id="agent-set" style="font-size:16px;" class="count-info" href="#"  onclick="mainRefresh()">
								<i class="fa fa-cog"></i>
						</a></li>-->
						<li class="dropdown"><a
							style="display:inline-block;vertical-align:middle; padding:15px 10px 15px 0px; font-weight:normal; font-size:13px;"
							data-toggle="dropdown" class="dropdown-toggle" href="#">
								<div class="user-circle"></div> 
								<span style="color:#005999;"
								class="text-muted text-xs user-name">代理商：${agencyId}<b
									class="caret"></b>
							   </span>
						</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a id="pass_reset">密码修改</a></li>
							<!-- <li><a class="J_menuItem" href="http://192.168.11.115:8080/qtfr/main.jsp" data-index="1">测试心跳</a>
								</li>
							 -->

								<li><a class="" href="javascript:void(0);"
									onclick="smrz()" data-index="1">实名认证</a></li>
								<li class="divider"></li>
								<li><a href="javascript:void(0);" onclick="logout()" >安全退出</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-angle-double-left"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab"
							data-id="index_v1.html">公告通知</a>
						<!--默认主页需在对应的选项卡a元素上添加data-id="默认主页的url"-->
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight" style="right:190px;">
					<i class="fa fa-angle-double-right"></i>
				</button>
				<div class="btn-group roll-nav roll-right" style="right:110px;">
					<button class="dropdown J_tabClose" data-toggle="dropdown"
						aria-expanded="false">
						关闭操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a style="width:110px;" href="javascript:void(0);" onclick="choseAgent()" class="roll-nav roll-right J_tabExit"><i
					class="fa fa-exchange"></i> 切换代理账户</a>

			</div>
			
	
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" name="iframe0" id="iframe0" width="100%" height="100%"
					src=""
					frameborder="0" data-id="index_v1.html" seamless></iframe>
				<!--默认主页需在对应的页面显示iframe元素上添加name="iframe0"和data-id="默认主页的url"-->
			</div>
			<!-- <div class="footer">
                <div class="pull-right">© 银联认证 2014-2015 .All Rights Reserved 版权所有
                </div>
            </div>-->
            <div class="row J_mainContent" id="content-main">
                <!--<iframe id="iframepage" class="J_iframe" name="iframe0" width="100%" height="100%" src="index_v1.html?v=4.0" frameborder="0" data-id="index_v1.html" seamless></iframe>
                默认主页需在对应的页面显示iframe元素上添加name="iframe0"和data-id="默认主页的url"-->
            </div>
		</div>
		<!--右侧部分结束-->
	</div>

	<%@ include file="/inc/footer.jsp"%>
