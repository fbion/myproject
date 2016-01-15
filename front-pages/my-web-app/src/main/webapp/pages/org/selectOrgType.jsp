<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织新增页面</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript">

	$(function (){
		MALQ_CODE.getSelectByCodeType($('#type'),'UAA_ORG_TYPE', 140, 80);		
		var comboBox = $('#type').ligerGetComboBoxManager();		
		var itype = $('#orgType', parent.document).val();
		comboBox.selectValue(itype?itype:'INNER_UAA_ORG');
		var str = window.location.href;
		var es = /\?id=/;
		es.exec(str);
		var isPage = es.exec(str)?RegExp.rightContext:'';
		if(isPage=='edit'){
			comboBox.setDisabled('');
			$('#type').css('color','black');
		}
		
		var tags = parent.window.orgAdd.cache.tags;
		//alert(itype+"::"+JSON.stringify(tags));
		if(itype==='EXTERNAL_UAA_ORG'){
			outOrg(tags);		
		}else{
			innerOrg(tags);
		}		
		$('#type').change(function(){
			$('#type_val').val()=='INNER_UAA_ORG'?innerOrg():outOrg();
		});
	});

	function innerOrg(tags){
		var str ='';
		var data = MALQ_CODE.getCodeData('INNER_UAA_ORG');
		$(data).each(function(){
			var d = this;
			var is = false;
			$(tags).each(function(){
				if(this==d.code || this.tag==d.code){
					is = true;return;
				}
			});
			str +='<li class="l-table-edit-td"><input type="radio" name="innerType" value="'+this.code+'" '+(is?'checked="checked"':'')+'/>'+this.name+'</li>';
		});				
		$('#option').html(str);
	}
	
	function outOrg(tags){
		var str ='',i=0;
		var data = MALQ_CODE.getCodeData('EXTERNAL_UAA_ORG');
		$(data).each(function(){
			var d = this;
			str += i==0 ?'<li class="l-table-edit-td">':'';
			var is = false;
			$(tags).each(function(){
				if(this==d.code || this.tag===d.code){
					is = true;return;
				}
			});
			str += i<2?'<span style="padding:10px;"><input type="checkbox" name="outType" id="'+this.id+'" value="'+this.code+'" '+(is?'checked="checked"':'')+'/>'+this.name+'</span>':'';
			i++;
			if(i==2){
				str +='</li>';
				i=0;
			}
		});			
		$('#option').html(str);
	}
	
	function f_select(){
		var input = $('form input');
		var type = $('#type_val').val();
		var vals = [], id='', name = '';
		$(input).each(function(){
			if(type == 'INNER_UAA_ORG'){
				id=$("input[name='innerType']:checked").val();return;
			}else{
				if($(this).attr('checked') && this.name=='outType'){
					vals.push($(this).val());
                }
			}
		});
		
		var result={};
		if(id==''){
			var data = MALQ_CODE.getCodeData('EXTERNAL_UAA_ORG');
			$(data).each(function(){
				var code = this.code;
				var tem = this.name;
				if(code==id){
					name=tem;return;
				}else{
					$(vals).each(function(){
						if(code == this+''){
							name += tem+',';return;
						}
					});
				}
			});
			if(name.substring(name.length-1)==','){
				name = name.substring(0, name.length-1);
			}			
			result['id']=vals.join(',');
			result['name']=name;
			result['type']='EXTERNAL_UAA_ORG';
		}else{
			result['id']=id;
			result['name']=MALQ_CODE.getCodeName('INNER_UAA_ORG',id);
			result['type']='INNER_UAA_ORG';
		}
		//alert(JSON.stringify(result));
		return result;
	}
</script>
</head>
<body style="padding: 10px; padding-left: 10px;">
	
	<div align="center" style="">
		<form id="subform">
		<table class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="width:15%;">类型：</td>
				<td align="left" class="l-table-edit-td" style="width: 30%;"><input id="type" type="text"/></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="width:15%;"></td>
				<td align="left" class="l-table-edit-td">
					<div id="option">
						<ul>
							<li class="l-table-edit-td"><input type="radio" name="innerType" value="1"/>内部组织</li>
							<li class="l-table-edit-td"><input type="radio" name="innerType" value="2"/>内部虚拟组织</li>
							<li class="l-table-edit-td"><input type="radio" name="innerType" value="3"/>内部部门</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
		</form>		
	</div>
</body>
</html>