/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var OrgList = function() {
		this.init();
	};
	OrgList.prototype = {
		config : {
			queryUrl : root + '/orgJson/getListPage.action',
			delUrl : root + '/orgJson/delete.action',
			treeUrl : root + '/orgJson/getList.action',
			isDeleteUrl : root + '/orgJson/isDelete.action',
			changeParent : root + '/orgJson/changeParent.action',
			bindSystemUrl : root + '/orgJson/bindSystem.action',
			userOrgId : parent.parent.INDEX_USER_ORGID,
			userOrgName : parent.parent.INDEX_USER_ORGNAME,
			mergeOrgUrl : root + '/orgJson/merge.action'
		},
		cache : {
			subPageId : null,
			orgType : null,
			grid : null,
			dialog : null,
			accordion : null,
			tree : null,
			tab : null,
			pTreeIdText : null,
			pTreeId : null,
			editId : null,
			searchParms : {},
			mergeOrg : {
				delId : '',
				retainId : ''
			},
			outSystems : null,
			checkOrgs : []
		},
		init : function() {
			this.render();
			this.initOrgTree();
			this.loadGird();
		},
		render : function() {
			var that = this;
			$('#pTreeIdText').text(that.config.userOrgName);
			$('#pTreeId').val(that.config.userOrgId);
			$('#orgType').val('INNER_UAA_ORG');

			// 布局
			$('#layout1').ligerLayout({
				leftWidth : '210',
				allowLeftResize : false,
				height : '100%',
				heightDiff : -1,
				space : 4,
				onHeightChanged : that._heightChanged
			});
			$('.l-layout-header').show();

			$('.l-layout-header-toggle').css('z-index', '2');

			// $(window).scrollTop() + $(window).height()

		},

		initOrgTree : function() {
			var that = this;
			that.cache.mytree = $('#accordion1')
					.orgTreeSelect(
							{
								width : '210',
								top : '-25',
								onClickTab : function(tabName) {
									var orgType = tabName == 'externalTab' ? 'EXTERNAL_UAA_ORG'
											: 'INNER_UAA_ORG';
									var text = tabName == 'externalTab' ? '外部组织'
											: '内部组织';
									$('#orgType').val(orgType);
									$('#queryOrgDiv').find('#pTreeId').val('');
									$('#queryOrgDiv').find('#pTreeIdText')
											.text(text);
									// var pid =
									// tabName=='externalTab'?'external-org-root':'inner-org-root';
									var parms = [
									// {name:'requestParam.equal.pid',
									// value:pid},
									{
										name : 'requestParam.equal.orgType',
										value : orgType
									} ];
									if (orgType == 'INNER_UAA_ORG'
											&& parent.parent.INDEX_USER_TYPE != 'admin') {
										parms
												.push({
													name : 'requestParam.equal.pid',
													value : parent.parent.INDEX_USER_ORGID
												});
										$('#queryOrgDiv').find('#pTreeId').val(
												parent.parent.INDEX_USER_ORGID);
										$('#queryOrgDiv')
												.find('#pTreeIdText')
												.text(
														parent.parent.INDEX_USER_ORGNAME);
									}
									that.cache.checkOrgs = [];// clear
									// selected org.
									that.cache.grid.set('parms', parms);
									that.cache.grid.changePage('first');
									that.cache.grid.loadData(true);

								},
								onClick : function(node) {
									var id = node.data.id;
									if (id) {
										$('#queryOrgDiv').find('#pTreeId').val(
												id);
										$('#queryOrgDiv').find('#pTreeIdText')
												.text(node.data.orgShortName);
										// $('#queryOrgDiv').find('#query1').click();//触发查询按钮
										var parms = that.cache.grid
												.get('parms');
										var tem = [];
										$(parms)
												.each(
														function() {
															if (this.name == 'requestParam.like.orgName'
																	|| this.name == 'requestParam.equal.pid')
																return true;
															tem.push(this);
														});
										parms = tem;
										parms.push({
											name : 'requestParam.equal.pid',
											value : id
										});
										that.cache.grid.set('parms', parms);
										that.cache.grid.changePage('first');
										that.cache.grid.loadData(true);
									}
								}
							});
		},

		refreshTree : function() {
			var that = this;
			var tree = that.cache.mytree;

		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.orgList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},

		loadGird : function() {
			var that = this;
			// 生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(
					window.frameElement.id, [ 'org-add', 'org-merge' ]);

			// 为添加按钮响应事件
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				if ('org-add' == this.permId) {
					this['click'] = that.add;
					myitems.push(this);
				} else if ('org-merge' == this.permId) {
					this['click'] = that.merge;
					myitems.push(this);
				}

			});

			// myitems.push({id:'344dddd', click:that.merge, text:'合并组织',
			// icon:'deletepic'});

			// 获取操作列功能权限
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [
					"org-edit", "org-delete", "org-system-bind",
					'org-change-pid', 'org-role-add' ]);

			var options = {};
			var columns = [
					{
						display : '组织简称',
						name : 'orgShortName',
						align : 'left',
						width : '15%',
						render : function(row) {
							return '<a href="javascript:window.orgList.showOrgDetail(\''
									+ row.uaaOrg.id
									+ '\');">'
									+ row.uaaOrg.orgShortName + '</a>';
						}
					},
					{
						display : '组织名称',
						name : 'orgName',
						align : 'left',
						width : '20%',
						isSort : false,
						render : function(row) {
							return row.uaaOrg.orgName;
						}
					},
					{
						display : '组织编码',
						name : 'orgCode',
						align : 'center',
						isSort : false,
						width : '10%',
						render : function(row) {
							return row.uaaOrg.orgCode;
						}
					},
					{
						display : '组织类型',
						align : 'center',
						width : '10%',
						isSort : false,
						// name: 'orgType',type: 'combobox', editor:
						// MALQ_UI.getCodeComboboxForEditor('UAA_ORG_TYPE'),
						render : function(row) {
							return MALQ_CODE.getCodeName('UAA_ORG_TYPE',
									row.uaaOrg.orgType);
						}
					},
					// { display: '联系人', align: 'left',
					// render : function(row){return row.uaaOrg.businessPerson;}
					// },
					// { display: '联系电话', align: 'center',
					// render :function(row){return row.uaaOrg.businessPhone;}
					// },
					{
						display : '操作',
						align : 'center',
						width : '30%',
						isSort : false,
						render : function(row) {
							var str = '';
							// 通过权限唯一标示生成操作列按钮
							$(funItems)
									.each(
											function() {
												if ('org-edit' == this.permId) {
													str += '<a href="javascript:window.orgList.modeify(\''
															+ row.uaaOrg.id
															+ '\')">修改</a>&nbsp;&nbsp;';
												}
												if ('org-delete' == this.permId) {
													str += '<a href="javascript:window.orgList.del(\''
															+ row.uaaOrg.id
															+ '\')">删除</a>&nbsp;&nbsp;';
												}
												if ('org-system-bind' == this.permId) {
													str += '<a href="javascript:window.orgList.bindSystem(\''
															+ row.uaaOrg.id
															+ '\')">关联系统</a>&nbsp;&nbsp;';
												}
												if ('org-change-pid' == this.permId) {
													str += '<a href="javascript:window.orgList.changeParent(\''
															+ row.uaaOrg.id
															+ '\')">变更上级</a>&nbsp;&nbsp;';
												}
												if ('org-role-add' == this.permId) {
													str += '<a href="javascript:window.orgList.toRole(\''
															+ row.uaaOrg.id
															+ '\',\''
															+ row.uaaOrg.orgShortName
															+ '\')">创建角色</a>&nbsp;&nbsp;';
												}
											});
							return str;
						}
					} ];

			options['columns'] = columns;
			options['checkbox'] = true;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'orgShortName';
			options['isChecked'] = that.__isChecked;
			options['onCheckRow'] = that.__onCheckRow;
			options['onCheckAllRow'] = that.__onCheckAllRow;
			// options['tree']={columnName: 'orgShortname'};
			// options['usePager']=false;
			options['parms'] = [ {
				name : 'requestParam.equal.pid',
				value : $('#pTreeId').val()
			}, {
				name : 'requestParam.equal.orgType',
				value : $('#orgType').val()
			} ]; // 设置默认进来的请求参数
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单

			MALQ_UI.setGridOptions(options, that.config.queryUrl);

			$('#orgGridList').ligerGrid(options);
			that.cache.grid = $('#orgGridList').ligerGetGridManager();
			$('#orgGridList').css('top', '3px');
			$('#pageloading').hide();// 加载图片
		},
		__isChecked : function(rowdata) {
			var that = window.orgList;
			var result = false;
			$(that.cache.checkOrgs).each(function() {
				if (this.id == rowdata.uaaOrg.id) {
					result = true;
					return true;
				}
			});
			return result;
		},
		__onCheckAllRow : function(checked, element) {
			var that = window.orgList;
			var rows = that.cache.grid.getSelecteds();
			if (!checked) {
				that.cache.checkOrgs = [];// clear array.
			} else {
				if (rows && rows.length == 2) {
					if (that.cache.checkOrgs != null
							&& that.cache.checkOrgs.length >= 2) {
						JSWK.clewBox('组织合并所选组织最多为两个.您已经选择了['
								+ that.cache.checkOrgs[0].name + ']与['
								+ that.cache.checkOrgs[1].name
								+ ']两个组织!请调整后，直接进行合并.',
								'clew_ico_fail', failShowTime);
						return;
					}
					$(rows).each(function() {
						that.cache.checkOrgs.push({
							id : this.uaaOrg.id,
							name : this.uaaOrg.orgName
						});
					});
				} else {
					if (rows && rows.length > 2)
						JSWK.clewBox('请选择两个组织进行合并,组织合并所选组织最多为两个!',
								'clew_ico_fail', failShowTime);
				}
			}
		},
		__onCheckRow : function(checked, data, rowid, rowdata) {
			var that = window.orgList;
			if (checked) {
				/*
				 * if (that.cache.checkOrgs != null &&
				 * that.cache.checkOrgs.length >= 2) {
				 * JSWK.clewBox('组织合并所选组织最多为两个.您已经选择了[' +
				 * that.cache.checkOrgs[0].name + ']与[' +
				 * that.cache.checkOrgs[1].name + ']两个组织!请直接进行合并，否则请先取消所选的组织.',
				 * 'clew_ico_fail', failShowTime); return; }
				 */
				that.cache.checkOrgs.push({
					id : data.uaaOrg.id,
					name : data.uaaOrg.orgName
				});
			} else {
				var tem = [];
				$(that.cache.checkOrgs).each(function() {
					if (this.id == data.uaaOrg.id) {
						return true;
					}
					tem.push(this);
				});
				that.cache.checkOrgs = tem;
			}
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.orgList;
			that.cache.grid.loadData(true);
			that.cache.mytree._refreshTree();
		},
		// 一般查询
		search : function() {
			var that = this;
			that.cache.checkOrgs = [];// clear array will be merged
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(
					function() {
						if (this.name == 'requestParam.like.orgName'
								|| this.name == 'requestParam.equal.pid')
							return true;
						tem.push(this);
					});
			parms = tem;

			var pid = $('#pTreeId').val();
			if (pid) {
				parms.push({
					name : 'requestParam.equal.pid',
					value : pid
				});
			}
			var orgName = $('#orgName_find').val();
			if (orgName) {
				parms.push({
					name : 'requestParam.like.orgName',
					value : '%' + orgName + '%'
				});
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 高级查询
		advancedSearch : function() {
			var that = window.orgList;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(
					function() {
						if (this.name == 'requestParam.like.orgName'
								|| this.name == 'requestParam.equal.pid')
							return true;
						tem.push(this);
					});
			parms = tem;
			var searchParms = {
				parms : [ {
					eName : 'pTreeId',
					name : 'requestParam.equal.pid'
				}, {
					eName : 'orgType',
					name : 'requestParam.equal.orgType'
				} ]
			};
			that.cache.grid.showMyFilter(parms, searchParms);
		},
		showOrgDetail : function(id) {
			var that = this;
			var url = 'org-info.jsp?id=' + id;
			that.cache.dialog = MALQ_UI
					.open(url, subHeight, subWidth, '组织详细信息');
		},
		add : function() {
			var that = window.orgList;
			that.cache.subPageId = 'add';
			var title = '添加组织信息';
			var url = 'org-add-system.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,
					that.success, that.cancel, title);
		},
		success : function(item, dialog) {
			dialog.frame.window.orgAdd.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		// 在修改页面显示要修改的记录的信息
		modeify : function(id) {
			var that = this;
			that.cache.subPageId = 'edit';
			var title = '修改组织信息';
			that.cache.editId = id;
			var url = 'org-add-system.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,
					that.success, that.cancel, title);
		},
		getTreePidAndPtext : function(data, obj) {
			var that = window.orgList;
			$(data).each(function() {
				if (obj.id == this.id) {
					obj['pid'] = this.pid;
					obj['text'] = this.text;
					return false;
				}
				if (this.children) {
					that.getTreePidAndPtext(this.children, obj);
				}
			});
			return obj;
		},
		// 删除组织结构信息
		del : function(id, text) {
			var that = this;
			JAjax(that.config.isDeleteUrl, {
				'id' : id
			}, 'json', function(data) {
				if (data != '1') {
					JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
				} else {
					$.ligerDialog.confirm(text ? text : '确定要删除吗？',
							function(yes) {
								if (yes) {
									JAjax(that.config.delUrl, {
										'model.uaaOrg.id' : id
									}, 'json', function(data) {
										if (data) {
											JSWK.clewBox('删除成功！',
													'clew_ico_succ');
											that.refreshGrid();
										} else {
											JSWK.clewBox('删除失败！',
													'clew_ico_fail',
													failShowTime);
										}
									}, function() {
										JSWK.clewBox('提交数据时发生异常',
												'clew_ico_fail', failShowTime);
									}, true);
								}
							});
				}
			}, function() {
				JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
			}, true);

		},
		bindSystem : function(id) {
			var that = this;
			that.cache.subPageId = id;
			// $('#subPageOrgId').val(id);
			var url = 'orgBindSystem.jsp';
			var title = '关联系统信息';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,
					function(item, dialog) {
						var fn = dialog.frame.orgBindSystem.f_save
								|| dialog.frame.window.orgBindSystem.f_save;
						var bindData = fn();
						if (bindData) {
							bindData['id'] = id;
							// alert(JSON.stringify(bindData))
							JAjax(that.config.bindSystemUrl, bindData, 'json',
									function(data) {
										if (data) {
											dialog.close();
											JSWK.clewBox('关联系统成功！',
													'clew_ico_succ');
										}
									}, function() {
										JSWK.clewBox('绑定权限信息时发生异常',
												'clew_ico_fail', failShowTime);
									}, true);
						}
					}, function(item, dialog) {
						dialog.close();
					}, title);
		},

		merge : function() {
			var that = orgList;
			// clear
			that.cache.mergeOrg.delId = '';
			that.cache.mergeOrg.retainId = '';
			that.cache.outSystems = null;

			var rows = that.cache.checkOrgs;// that.cache.grid.getCheckedRows();
			if (!rows || rows.length != 2) {
				var _names = '';
				$(rows).each(function() {
					_names += this.name + ';';
				});

				if (rows.length < 2)
					JSWK.clewBox('请选择两个组织进行合并,组织合并所选组织最多为两个!', 'clew_ico_fail',
							failShowTime);
				else
					JSWK.clewBox('组织合并所选组织最多为两个.您已经选择了['
							+ that.cache.checkOrgs[0].name + ']与['
							+ that.cache.checkOrgs[1].name
							+ ']两个组织!请调整后,直接进行合并.', 'clew_ico_fail',
							failShowTime);

				return;
			}

			var orgname1 = rows[0].name;// rows[0].uaaOrg.orgShortName;
			var orgname2 = rows[1].name;// rows[1].uaaOrg.orgShortName;

			// check each other
			// var parentOrgid1 = '';
			// var parentOrgid2 = '';
			// try{
			// parentOrgid1 = rows[0].topologys[0].parentOrgid;
			// }catch(e){}
			// try{
			// parentOrgid2 = rows[1].topologys[0].parentOrgid;
			// }catch(e){}

			var url = 'mergeOrg.jsp';
			var purl = "mergeOrgPreview.jsp";
			var title = '合并组织';
			that.cache.dialog = MALQ_UI
					.open_button(
							url,
							subHeight,
							subWidth,
							function(item, dialog) {
								var fn = dialog.frame.window.mergeOrg
										.f_select();
								if (fn.flag) {
									if (fn.flag == "preview") { // 预览

										// if(parentOrgid1 != parentOrgid2){
										// JSWK.clewBox('选择的两个组织必须在同一组织下面!','clew_ico_fail',failShowTime);
										// //clear ischeckerror
										// if(dialog.frame.window.mergeOrg.cache.isCheckError==true){
										// dialog.frame.window.mergeOrg.cache.isCheckError
										// = false;
										// }
										// dialog.close();
										// return;
										// }

										if (dialog.frame.window.mergeOrg.cache.isCheckError == true) {
											JSWK
													.clewBox(
															'营业执照号，联系电话，组织编码一致的情况下才可合并!',
															'clew_ico_fail',
															failShowTime);
											dialog.frame.window.mergeOrg.cache.isCheckError = false;
											// dialog.close();
											// hide submit button
											$(".l-dialog-btn-inner")
													.each(
															function() {
																if ($(this)
																		.text() == "确定") {
																	$(this)
																			.parent()
																			.css(
																					"display",
																					"none");
																	return true;
																}
															});
											return;
										}

										if (dialog.frame.window.mergeOrg.cache.isCrossOfOutSystem == true) {
											JSWK.clewBox('绑定的外部系统存在交集，不允许合并!',
													'clew_ico_fail',
													failShowTime);
											dialog.frame.window.mergeOrg.cache.isCrossOfOutSystem = false;
											// dialog.close();
											$(".l-dialog-btn-inner")
													.each(
															function() {
																if ($(this)
																		.text() == "确定") {
																	$(this)
																			.parent()
																			.css(
																					"display",
																					"none");
																	return true;
																}
															});
											return;
										}

										that.cache.mergeOrg = fn;
										that.cache.outSystems = dialog.frame.window.mergeOrg.cache.outSystems;
										dialog.setUrl(purl);

										// 取消 --> 上一步
										$(".l-dialog-btn-inner")
												.each(
														function() {
															if ($(this).text() == "取消") {
																$(this).text(
																		"上一步");
																return true;
															}
														});

									} else if (fn.flag == "submit") {
										// 确定提交
										$.ligerDialog
												.confirm(
														'确定要合并吗？',
														function(yes) {
															if (yes) {
																$.ligerDialog
																		.prompt(
																				'请输入任务名称',
																				'',
																				function(
																						yes1,
																						value) {
																					if (yes1) {
																						value = $
																								.trim(value);
																						var now = new Date()
																								.Format("yyyy-MM-dd hh:mm:ss");
																						var _msg = '组织合并['
																								+ orgname1
																								+ ']['
																								+ orgname2
																								+ ']';
																						var _taskname = (value == '' ? (_msg + now)
																								: (value + now));

																						var param = {
																							'delId' : that.cache.mergeOrg.delId,
																							'retainId' : that.cache.mergeOrg.retainId,
																							'taskName' : _taskname
																						};

																						JAjax(
																								that.config.mergeOrgUrl,
																								param,
																								'json',
																								function(
																										data) {
																								},
																								function() {
																								},
																								true);

																						// tip
																						// delay
																						// 2s
																						var manager = $.ligerDialog
																								.waitting('正在合并中...请在合并任务处查看进度!');
																						setTimeout(
																								function() {
																									that.cache.checkOrgs = [];
																									manager
																											.close();
																									dialog
																											.close();
																									that
																											.refreshGrid();
																								},
																								2000);
																					}
																				});

															} else {
																dialog.close();
															}
														});

									}
								}
							},
							function(item, dialog) {
								var fn = dialog.frame.window.mergeOrg
										.f_cancel();
								if (fn.flag) {
									if (fn.flag == "pre") {
										dialog.setUrl(url);
										$(".l-dialog-btn-inner")
												.each(
														function() {
															if ($(this).text() == "上一步") {
																$(this).text(
																		"取消");
																return true;
															}
														});
									} else {
										dialog.close();
									}
								} else {
									dialog.close();
								}
							}, title);

		},

		changeParent : function(id) {
			var that = this;
			var url = 'changeOrgParent.jsp?id=' + id;
			var title = '变更上级组织信息';
			that.cache.dialog = MALQ_UI
					.open_button(
							url,
							subHeight,
							subWidth,
							function(item, dialog) {
								var fn = dialog.frame.changeOrgParent.f_select
										|| dialog.frame.window.changeOrgParent.f_select;
								var data = fn();
								var category = data ? data.category : '';
								var pid = data ? data.uaaOrg.id : '';
								if (pid && category) {
									JAjax(that.config.changeParent, {
										'id' : id,
										'model.pid' : pid,
										'stats' : category
									}, 'json', function(data) {
										that.alert(data, 'clew_ico_succ');
										dialog.close();
										that.refreshGrid();
									}, function() {
										JSWK.clewBox('变更上级组织时发生异常',
												'clew_ico_fail', failShowTime);
									}, true);
								}
							}, function(item, dialog) {
								dialog.close();
							}, title);
		},

		toRole : function(id, orgShortName) {
			var menuData = parent.window.indexPage.menuData;
			var roleMenuId = '';
			$(menuData).each(function() {
				if (this.pageUrl === '/pages/role/role-list.jsp') {
					roleMenuId = this.id;
					return;
				}
			});
			parent.window.indexPage.f_addTab(roleMenuId);
			parent.window.indexPage.orgAddRole_Id = id;
			parent.window.indexPage.orgAddRole_Name = orgShortName;
		},

		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};
	Date.prototype.Format = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	};

	$(document).ready(function() {
		window.orgList = new OrgList();
	});
})();