<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/inc/header.jsp"%>

<!-- CSS文件 -->
<link href="${pageContext.request.contextPath}/static/css/plugins/chosen/chosen.css"
	rel="stylesheet">

<!-- jqgrid-->
<link href="${pageContext.request.contextPath}/static/css/plugins/jqgrid/ui.jqgrid.css?0820"
	rel="stylesheet">

<link href="${pageContext.request.contextPath}/static/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<!-- Peity -->
<script src="${pageContext.request.contextPath}/static/js/plugins/peity/jquery.peity.min.js"></script>

<!-- jqGrid -->
<script	src="${pageContext.request.contextPath}/static/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script	src="${pageContext.request.contextPath}/static/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>

<!-- Chosen -->
<script
	src="${pageContext.request.contextPath}/static/js/plugins/chosen/chosen.jquery.js"></script>

<!-- Data picker -->
<script
	src="${pageContext.request.contextPath}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>

<!-- 自定义js -->
<script
	src="${pageContext.request.contextPath}/static/js/content.js?v=1.0.0"></script>



</head>

<body class="white-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div style="margin:0px 20px 0px 10px;">
				<form method="get" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-1 control-label">终端编号</label>
						<div class="col-sm-3">
							<input id = "terminal_id" type="text" class="form-control">
						</div>

						<label class="col-sm-1 control-label">机构名称</label>
						<div class="col-sm-3">
							<input id = "organ_id" type="text" class="form-control">
						</div>

						<label class="col-sm-1 control-label">终端类型</label>
						<div class="col-sm-3">
							<input id = "treminal_type" type="text" class="form-control">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-1 control-label">机构名称</label>
						<div class="col-sm-3">
							<div class="input-group" style="width:100%;">
								<select data-placeholder="选择省份..." class="chosen-select"
									style="width:100%;" tabindex="2">
									<option value="">请选择省份</option>
									<option value="110000" hassubinfo="true">北京</option>
									<option value="120000" hassubinfo="true">天津</option>
									<option value="130000" hassubinfo="true">河北省</option>
									<option value="140000" hassubinfo="true">山西省</option>
								</select>
							</div>
						</div>

						<label class="col-sm-1 control-label">选择日期</label>
						<div class="col-sm-3">
							<div class="input-group date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input id="datepicker" type="text" class="form-control"
									value="2014-11-11">
							</div>
						</div>

						<div class="col-sm-4" style=" text-align:right;">
							<button id ="search_button" type="button" class="btn btn-w-m btn-primary">查询</button>
						</div>
					</div>
				</form>

			</div>
			<div class="jqGrid_wrapper">
				<div class="jqGrid_wrapper">
					<table id="table_list_2"></table>
					<div id="pager_list_2"></div>
				</div>
			</div>
		</div>
	</div>

	<!-- Page-Level Scripts -->
	<script>
        $(document).ready(function () {
    		$('.chosen-select').chosen();
			$( "#datepicker" ).datepicker();
	
            $.jgrid.defaults.styleUI = 'Bootstrap';
            // Configuration for jqGrid Example 2
            $("#table_list_2").jqGrid({
            	url: basePath + '/user/list',
            	mtype : "POST",
                datatype: "json",
                height: 310,
                prmNames:{
                	page:"pageNum",rows:"pageSize", sort: "sidx",order: "order", search:"search", nd:"nd", npage:null
                },
                //返回
                jsonReader:{
				    root: "result",
				    page: "page.pageNum",
				    total: "page.pages",
				    records: "page.total",
				    /* repeatitems: !0,
				    cell: "cell",
				    id: "id",
				    userdata: "userdata",
				    subgrid: {
				        root: "rows",
				        repeatitems: !0,
				        cell: "cell"
				    } */
				},
                autowidth: true,
                shrinkToFit: true,
                rowNum: 3,
                rowList: [1, 3, 5],
                colNames: ['序号', '日期'],
                colModel: [
                    {
                        name: 'id',
                        index: 'id',
                        editable: true,
                        width: 60,
                        sorttype: "int",
                        search: true
                    },
                   	{
                        name: 'name',
                        index: 'name',
                        editable: true,
                        width: 100
                    }
                ],
                pager: "#pager_list_2",
                viewrecords: true,
                add: true,
                edit: true,
                addtext: 'Add',
                edittext: 'Edit',
                hidegrid: false,
				multiselect: true
            });

            // Add selection
            $("#table_list_2").setSelection(4, true);


            // Setup buttons
            $("#table_list_2").jqGrid('navGrid', '#pager_list_2', {
                edit: true,
                add: true,
                del: true,
                search: true	
            }, {
                height: 200,
                reloadAfterSubmit: true
            });

            // Add responsive to jqGrid
            $(window).bind('resize', function () {
                var width = $('.jqGrid_wrapper').width();
                $('#table_list_2').setGridWidth(width);
            });
            
            
            //条件查询
			$("#search_button").click(function(){
				terminalSearch();
			});
            
        });
        
        function terminalSearch() {
       		var params = {};
	        params.terminalId = $("#terminal_id").val();
	        $("#table_list_2").jqGrid('setGridParam',{
	        	mtype: 'POST',
	            postData : params, //发送数据 
	            datatype : "json", //发送数据 
	            page:1 
       		 }).trigger("reloadGrid"); //重新载入 
        }
    </script>

	<%@ include file="/inc/footer.jsp"%>