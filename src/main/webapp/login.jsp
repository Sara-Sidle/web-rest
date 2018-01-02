<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/inc/header.jsp"%>
<title>分润管理系统-登录</title>

<style>

body {
	background:
		url(${pageContext.request.contextPath}/static/img/dls-bg.jpg)
		no-repeat center;
}

.login-logo {
	text-align: center;
	margin-bottom: 60px;
}

.img-logo {
	width: 220px;
}

.login-input {
	border: none;
	border-bottom: 1px solid #eee;
	background-color: transparent;
	width: 100%;
	font-size: 14px;
	padding: 8px 5px;
	outline: none;
	-webkit-appearance: none;
}

.form-group input:focus {
	border: none;
}

.form-group input:focus     ~ .bar:before,.form-group input:focus     ~
	.bar:after {
	width: 50%;
}

.form-group .bar {
	background: #eee;
	width: 100%;
	height: 1px;
	margin-top: -1px;
}

.form-group .bar:before,.form-group .bar:after {
	content: '';
	position: absolute;
	background: #1a77b8;
	width: 0;
	height: 2px;
	-webkit-transition: .2s ease;
	transition: .2s ease;
}

.form-group .bar:before {
	left: 45%;
}

.form-group .bar:after {
	right: 45%;
}

.form-group {
	margin-top: 10px;
}

.login-btn {
	background: #1a77b8;
	font-size: 14px;
	color: #fff;
	width: 120px;
	height: 40px;
	border: none;
	margin-top: 5px;
	outline: none;
	-webkit-appearance: none;
}

.login-btn.color-1 {
	background-color: #1a77b8;
}

.btn-border.color-1 {
	background-color: transparent;
	color: #fff;
}

.material-design {
	position: relative;
}

.material-design canvas {
	opacity: 0.2;
	position: absolute;
	top: 0;
	left: 0;
}

#login-form .nav-tabs>li>a {
	font-weight: normal;
}

#code-btn {
	position: absolute;
	right: 0;
	margin-right: 5%;
	height: 30px;
	line-height: 30px;
	width: 100px;
	border: none;
	background-color: #1a77b8;
	color: #fff;
	font-size: 12px;
	text-align: center;
}

.telcode-btn {
	border: none;
	background-color: #fff;
	text-align: right;
	font-size: 12px;
	height: 30px;
	width: 100px;
	color: #1a77b8;
	outline: none;
}

.error {
	color: red;
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
</style>
<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
<script>
	if (window.top !== window.self) {
		window.top.location = window.location;
	}
</script>

<script type="text/javascript">
	/* 验证码倒计时 */
	var wait = 60;
	/* 手机号正则 */
	var phoneRule = "^[1][3-8]\\d{9}$";
	
	//账号密码登录
	function submit() {
		var param = {};
		//PHONESMS("PhoneSms"), PHONEPWD("PhonePwd"), ACCOUNTPWD("AccountPwd");
		param.username = $("#username").val();
		param.password = hex_md5($("#password").val());
		param.loginType = $("#loginType").val();
		param.code = $("#imgCode").val();
		if(param.username == ""){
			layerAlert("请输入正确的账号");	
			return false;
		}
		if($("#password").val() == ""){
			layerAlert("密码不能为空");
			return false;
		}
		if(param.code == ""){
			layerAlert("验证码不能为空");
			return false;
		}
		$.ajax({
			url : basePath + "/dologin",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify(param),
			dataType : "json",
			success : function(data) {
				if (data.code == "0000") {
					window.location.href = "${pageContext.request.contextPath}/choseAgent";
				} else if (data.code == "2002") {
					$("#error_info").text(data.msg);
					change();
				} else {
					change();
					$("#error_info").text(data.msg);
				}
				
			},
		});
	}

	//验证码
	function submitSms() {
		var param = {};
		var phone = $("#user_phone").val();
		var password = $("#smsCode").val();
		param.loginType = $("#loginType1").val();
		if (phone.match(phoneRule) == null || phone.length != 11) {
			layerAlert("请输入正确的手机号");
			return false;
		}
		if(password == "") {
			layerAlert("验证码不能为空");
			return false;
		}
		param.username = phone;
		param.password = password;
		$.ajax({
			url : basePath + "/dologin",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify(param),
			dataType : "json",
			success : function(data) {
				if (data.code == "0000") {
					window.location.href = "${pageContext.request.contextPath}/choseAgent";
				} else {
					$("#error_info1").text(data.msg);
				}
			},
		});
	}
	function submitAccount() {
		var param = {};
		param.username = $("#account").val();
		param.password = hex_md5($("#accountPassword").val());
		param.loginType = $("#loginType2").val();
		param.code = $("#imgCode1").val();
		
		if(param.username == ""){
			layerAlert("请输入正确的账号");
			return false;
		}
		if($("#accountPassword").val() == ""){
			layerAlert("密码不能为空");
			return false;
		}
		if(param.code == "" || param.code == null){
			layerAlert("验证码不能为空");
			return false;
		}
		
		$.ajax({
			url : basePath + "/dologin",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify(param),
			dataType : "json",
			success : function(data) {
				if (data.code == "0000") {
					window.location.href = "${pageContext.request.contextPath}/choseAgent";
				} else if (data.code == "2002") {
					$("#error_info2").text(data.msg);
					change1();
				} else {
					change1();
					$("#error_info2").text(data.msg);
				}
			},
		});
	}
	//验证码发送
	function sendSms(val) {
		var param = {};
		var phone = $("#user_phone").val();
		if (phone.match(phoneRule) == null || phone.length != 11) {
			layerAlert("请输入正确的手机号");
			return false;
		}
		settime(val);
		param.phoneNo = phone;
		$.ajax({
			url : basePath + "/sendMsg",
			type : "POST",
			contentType : "application/json",
			data : JSON.stringify(param),
			dataType : "json",
			success : function(data) {
				if (data.code == "0000") {
					layerAlert("短信发送成功");
				} else if (data.code == "3000") {
					layerAlert("今天验证码已达上限，请明日再试！");
				} else {
					wait = 0;
					layerAlert("短信发送失败");
				}
			},
		});
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
	
	function change() {
		$("#Kaptchajpg").attr("src", "kaptcha?rnd=" + Math.random());
	}
	
	function change1() {
		$("#Kaptchajpg1").attr("src", "kaptcha?rnd=" + Math.random());
	}
	
	$(function () {
		
	});
</script>

</head>

<body style="background-color:#f0f6f9;">

	<div class="middle-box text-center loginscreen  animated fadeInDown"
		style="padding-top:100px;">
		<div>
			<div class="login-logo">
				<img class="img-logo"
					src="${pageContext.request.contextPath}/static/img/logo-1.png" />
			</div>
			<div id="login-form" class="tabs-container" style="opacity:0.8;">
				<ul class="nav nav-tabs">
					<li class="active"><a data-toggle="tab" href="#tab-1"
						aria-expanded="true"> 手机号登录</a></li>
					<li class=""><a data-toggle="tab" href="#tab-2"
						aria-expanded="false">短信登录</a></li>
					<li class=""><a data-toggle="tab" href="#tab-3"
						aria-expanded="false">账号登录</a></li>
				</ul>
				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<div class="panel-body">
							<div id="error_info" class="error" style="text-align:left;margin-left:5px;"></div>
							<div class="form-group">
								<input class="login-input" type="text" placeholder="手机号" maxlength="11"
									id="username" required=""> <input class="login-input"
									type="hidden" id="loginType" value="PhonePwd">
								<div class="bar"></div>
							</div>
							<div class="form-group">
								<input class="login-input" type="password" placeholder="密码"
									id="password" required="">
								<div class="bar"></div>
							</div>
							<div class="form-group">
							<img src="kaptcha" id = "Kaptchajpg" style=" position:absolute; right:0;margin-right:5%; height:30px;" onclick="change()"/>								
								<input class="login-input" type="text" placeholder="验证码"	required="" id = "imgCode">
								<div class="bar"></div>
							</div>
							<div class="form-group">
								<button type="submit" class="login-btn color-1 material-design"
									data-color="#2f5398" onclick="submit()">登 录</button>
							</div>
						</div>
					</div>
					<div id="tab-2" class="tab-pane">
						<div class="panel-body">
							<div id="error_info1" class="error" style="text-align:left;margin-left:5px;"></div>
							<div class="form-group" style="margin-bottom:20px;">
								<input class="login-input" type="text" placeholder="手机号"
									id="user_phone" required=""> <input class="login-input"
									type="hidden" id="loginType1" value="PhoneSms">
								<div class="bar"></div>
							</div>
							<div class="form-group" style="margin-bottom:40px;">
								<input class="login-input" type="text" placeholder="短信验证码"
									id="smsCode" required="">
								<div class="bar"></div>
								<div style="margin-top: -32px;text-align: right;">
									<input class="telcode-btn" type="button" id="send_btn"
										value="获取验证码" onclick="sendSms(this)" maxlength="11" />
								</div>
							</div>
							<div class="form-group">
								<button type="submit" class="login-btn color-1 material-design"
									data-color="#2f5398" onclick="submitSms()">登 录</button>
							</div>
						</div>
					</div>
					<div id="tab-3" class="tab-pane">
						<div class="panel-body">
							<div id="error_info2" class="error" style="text-align:left;margin-left:5px;"></div>
							<div id="error_info" class="error" style="text-align:left;margin-left:5px;"></div>
							<div class="form-group">
								<input class="login-input" type="text" placeholder="账号" id="account" required=""> 
								<input class="login-input"	type="hidden" id="loginType2" value="AccountPwd">
								<div class="bar"></div>
							</div>
							<div class="form-group">
								<input class="login-input" type="password" placeholder="密码" id="accountPassword" required="">
								<div class="bar"></div>
							</div>
							<div class="form-group">
								<img
									src="kaptcha" id = "Kaptchajpg1" 
									style=" position:absolute; right:0;margin-right:5%; height:30px;" onclick="change1()"/>
								<input class="login-input" type="text" placeholder="验证码" id = "imgCode1"
									required="">
								<div class="bar"></div>
							</div>
							<div class="form-group">
								<button type="submit" class="login-btn color-1 material-design"
									data-color="#2f5398" onclick="submitAccount()">登 录</button>
							</div>
						</div>
					</div>
					
				</div>
				<!--  <a id="middle-tips">重置密码</a>&nbsp;&nbsp;&nbsp; -->

			</div>
			<!--  <div class="login-content">
                    <div class="form-group">
                        <input class="login-input" type="text"placeholder="用户名"  id="username" required="">
        				<div class="bar"></div>
                    </div>
                    <div class="form-group">
                        <input class="login-input" type="password" placeholder="密码"  id="password" required="">
        				<div class="bar"></div>
                    </div>
                    <div class="form-group">
                    	<img src="${pageContext.request.contextPath}/static/img/code-img.png" style=" position:absolute; right:0;margin-right:15%; height:30px;" />
                        <input class="login-input" type="text" placeholder="验证码" required="">
        				<div class="bar"></div>
                    </div>
                    <div class="form-group">
                    	<button type="submit" class="login-btn color-1 material-design" data-color="#2f5398" onclick="submit()">登 录</button>
                    </div>
            </div>-->
		</div>
	</div>

	<!-- particles.js container 
    <div id="particles-js"></div>-->

	<!-- particles 
    <script src="${pageContext.request.contextPath}/static/js/plugins/particles/particles.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/plugins/particles/app.js"></script>-->
	<%@ include file="/inc/footer.jsp"%>