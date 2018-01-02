$(function () {

var json = '[' +
		'{' +
        '"text": "系统管理",' +
        '"nodes": [' +
        '{' +
        '"text": "系统管理",' +
		'"icon": "glyphicon glyphicon-cog",'+
		'"url": "user_manage.html"'+
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "机构管理",' +
        '"nodes": [' +
        '{' +
        '"text": "机构管理",' +
		'"icon": "glyphicon glyphicon-cog",'+
		'"url": "index_notice.html"'+
        '},' +
        '{' +
        '"text": "机构审核",' +
		'"icon": "glyphicon glyphicon-cog",'+
		'"url": "index_notice.html"'+
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "系统管理",' +
        '"nodes": [' +
        '{' +
        '"text": "系统管理",' +
		'"icon": "glyphicon glyphicon-cog",'+
		'"url": "index_notice.html"'+
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "机构管理",' +
        '"nodes": [' +
        '{' +
        '"text": "机构管理",' +
		'"icon": "glyphicon glyphicon-cog",'+
		'"url": "index_notice.html"'+
        '},' +
        '{' +
        '"text": "机构审核",' +
		'"icon": "glyphicon glyphicon-cog",'+
		'"url": "index_notice.html"'+
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "机构管理",' +
		'"icon": "glyphicon glyphicon-cog"'+
        '}' +
        ']';

	$('#menuList').treeview({
          data: json,         // 数据源
		  levels: 1,
		  nodeIcon: '',
		  emptyIcon: 'fa fa-home',    //没有子节点的节点图标
          multiSelect: false,    //多选
          onNodeChecked: function (event,data) {
              alert("---"+data.nodeId+"------");
          },
          onNodeSelected: function (event, data) {
              document.getElementById("iframepage").src = data.url;
			  $("#iframepage").removeAttr("name");
			  $("#iframepage").removeAttr("data-id");
          }
     });

});
