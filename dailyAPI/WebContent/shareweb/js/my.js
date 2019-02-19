// JavaScript Document
$(function(){ 
	//----------city---------------------------
	$('.w-search-gps').click(function(e) {
        $('.w-search-inner .w-search-gps ul').toggle();
    });
	//-----------tab------------------------
	(function(){ 
		$('.tab h4 span').click(function(e) {
            $(this).addClass('current').siblings().removeClass('current');
			$('.tab ul li:eq('+$(this).index()+')').show().siblings().hide();
        });
	})();
	
	(function(){ 
		$('.redeem-tab h4 span').click(function(e) {
            $(this).addClass('current').siblings().removeClass('current');
			$('.redeem-tab ul li:eq('+$(this).index()-1+')').show().siblings().hide();
        });
		
	})();
//--------------option选择规格-------------------------
	(function(){ 
				$('.option').click(function(e) {
					$(this).addClass('option_selcted').siblings().removeClass('option_selcted');
				});
				$('.option_no').click(function(e) {
                    $(this).removeClass('option_selcted');
                });
	})();




//-------------------选择商品数量-------------------------
	function number(){ 
		var add=$('#plus');//添加数量
		var reduce=$('#minus');//减少数量
		var num_ori="";//数量的初始值
		var num_txt=$('#buyNum');//接受数量的文本框
		
		//添加数量事件------------------
		add.click(function(e) {
			num_ori=$('#buyNum').val();
			num_ori++;
			num_txt.val(num_ori);
		});
		
		//减少数量事件-------------------
		reduce.click(function(e) {
			num_ori=$('#buyNum').val();
			//只有当文本框内的值大于0的时候才执行减去事件
			if( num_ori>0 ){ 
				//并且当文本框的值为1的时候，减去后文本框直接清空值，不显示0 
				if( num_ori==1 ){ 
					num_ori--;
					num_txt.val("");
				}else{ 
					//否则就执行减减
					num_ori--;
					num_txt.val(num_ori);
				}
			}
		});
	}
	number();
	
})




































