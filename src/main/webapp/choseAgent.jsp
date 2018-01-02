<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/inc/header.jsp"%>

<title>代理商</title>

<style>
	.dls-bg{
		background:url(${pageContext.request.contextPath}/static/img/dls-bg.jpg) no-repeat center bottom;
		width:100%;
		height:100%;
		position:absolute;
		bottom:0;
		top:0;
		left:0;
		right:0;
	}
	.dls-list{
		background:#fff;
		width:340px;
		height:240px;
		border-radius:5px;
		box-shadow:1px 1px 5px #888;
		opacity:0.8;
		margin-top:35%;
		
	}
	.gohome-btn{
		background: #1a77b8;
		font-size:14px;
		color:#fff;
		width:120px;
		height:40px;
		outline:none;
		-webkit-appearance:none;
		-moz-appearance:none;
		padding:12px 35px;
	}
	.gohome-btn:hover{
		color:#fff;
	}

</style>
</head>

<body>
	<div class="dls-bg">
    	<div class="middle-box">
            <div class="dls-list ibox-content">
                <div class="form-group">
                	<div style="text-align:center; margin:10px 0px 20px 0px;font-size:16px; color:#333;">选择代理账户</div>
                   <!--  <div style="margin-bottom:10px;">请选择需要的代理账户</div> -->
                    <div class="input-group">
                        <select id="agency_id" name="agency_id" data-placeholder="选择代理账户" class="chosen-select" style="width:300px;" tabindex="2">
                      		<c:forEach items="${requestScope.agentMap}" var="each2">
								<option value="${each2.agencyId}">${each2.agencyId}(${each2.companyName})</option>
							</c:forEach>
                        </select>
                    </div>
                </div>
                <div id='gomain'  style="text-align:center;margin-top:80px;">
                	<a class="gohome-btn">进入主页</a>
                </div>
            </div>
    	</div>
    </div>
     
    
    
    <!-- Page-Level Scripts -->
    <script>
        $(document).ready(function () {
    		$('.chosen-select').chosen();
    		
    		//如果是账户登录，则直接进入下一页
    		if('${loginType}' == "AccountPwd"){
    			window.location.href = basePath+"/"+'${agencyId}';
    		};
    		
    		$('#gomain').click(function (obj) {
    			
    			var agencyId= $('#agency_id').val();
        		
    			window.location.href = basePath+"/"+ agencyId;
    			
    			/*
    			$.post(basePath + "/main", {
    				agencyId : agencyId
    			}, function(data) {
    				console.log(JSON.stringify(data));
    			}, "json");
    			
    			 */
    			
    			/**
    			
    			$.ajax({
        			url : "http://127.0.0.1:8080/qtfr/platform/platform.do?method=check4Agent",
        			type : "POST",
        			contentType : "application/json",
        			//data : JSON.stringify(param),
        			data : {
        				agencyId : agencyId,
        				mobile : '18678876092',
        				sessionId : 'alkdfalkdjf'
        			},
        			dataType : "json",
        			success : function(data) {
        				if (data.code == "0000") {
        					window.location.href = basePath+"/home";
        				}
        				else{
        					alert(data.msg);
        				}
        			}
        		});
    			
    			**/
     
    		});
    		
    		 
		});
        
        
        
         
	</script>
