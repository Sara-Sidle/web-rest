function hintWindow(content) {
	layer.open({
		type : 1,
		title : "温馨提示",// title:false 不显示标题栏
		area : [ '260px', '150px' ],
		offset : 'rb', // 具体配置参考：offset参数项
		content : '<div style="padding: 20px 80px;">' + content + '</div>',
		btn : '确定',
		btnAlign : 'c', // 按钮居中
		shade : 0, // 不显示遮罩
		time: 5000, //自动关闭
		yes : function() {
			layer.closeAll();
		}
	});
}
function layerAlert(content){
//墨绿深蓝风
layer.alert(content, 
 {
  skin: 'layui-layer-molv' //样式类名
  ,closeBtn: 0
 }
);
}