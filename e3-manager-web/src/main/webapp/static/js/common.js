/**
 *
 * @param format
 * @return {*}
 */
Date.prototype.format = function (format) {
  var o = {
    'M+': this.getMonth() + 1, //month
    'd+': this.getDate(), //day
    'h+': this.getHours(), //hour
    'm+': this.getMinutes(), //minute
    's+': this.getSeconds(), //second
    'q+': Math.floor((this.getMonth() + 3) / 3), //quarter
    'S': this.getMilliseconds() //millisecond
  }
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
    }
  }
  return format
}

var E3 = {
  // 编辑器参数
  kingEditorParams: {
    //指定上传文件参数名称
    filePostName: 'uploadFile',
    //指定上传文件请求的url。
    uploadJson: '/manager/item/pic/upload',
    //上传类型，分别为image、flash、media、file
    dir: 'image'
  },

  init: function (data) {
    // 初始化图片上传组件
    this.initPicUpload(data)
    // 初始化选择类目组件
    this.initItemCat(data)
  },
  // 初始化图片上传组件
  initPicUpload: function (data) {
    $('.picFileUpload').each(function (i, e) {
      var _ele = $(e)
      // 选择兄弟节点
      _ele.siblings('div.pics').remove()
      _ele.after('<div class="pics"><ul></ul></div>')
      // 回显图片
      if (data && data.pics) {
        var imgs = data.pics.split(',')
        imgs.forEach(function (img) {
          if ($.trim(img).length > 0) {
            _ele.siblings('.pics')
                .find('ul')
                .append('<li><a href="' + img + '" target="_blank"><img src="' + img + '" width="80" height="50"></a></li>')
          }
        })
      }
      // 给"上传图片按钮"绑定click事件
      $(e).click(function () {
        var form = $(this).parentsUntil('form').parent('form')
        // 打开图片上传窗口
        KindEditor.editor(E3.kingEditorParams).loadPlugin('multiimage', function () {
          var editor = this
          editor.plugin.multiImageDialog({
            clickFn: function (urlList) {
              var imgArray = []
              KindEditor.each(urlList, function (i, data) {
                var url = data.url
                imgArray.push(url)
                form.find('.pics ul')
                    .append('<li><a href="' + url + '" target="_blank"><img src="' + url + '" width="80" height="50"></a></li>')
              })
              form.find('[name=image]').val(imgArray.join(','))
              editor.hideDialog()
            }
          })
        })
      })
    })
  },

  // 初始化选择类目组件
  initItemCat: function (data) {
    $('.selectItemCat').each(function (i, e) {
      var _ele = $(e)
      if (data && data.cname) {
        _ele.after('<span style="margin-left: 10px;">' + data.cname + '</span>')
      } else {
        _ele.after('<span style="margin-left: 10px;"></span>')
      }
      _ele.unbind('click').click(function () {
        $('<div>').css({padding: '5px'}).html('<ul>').window({
          width: '500',
          height: '450',
          modal: true,
          closed: true,
          iconCls: 'icon-save',
          title: '选择类目',
          onOpen: function () {
            var _win = this
            $('ul', _win).tree({
              url: '/manager/item/cat/list',
              animate: true,
              onClick: function (node) {
                if ($(this).tree('isLeaf', node.target)) {
                  // 填写到cid中
                  _ele.parent().find('[name=cid]').val(node.id)
                  _ele.next().text(node.text).attr('cid', node.id)
                  $(_win).window('close')
                  if (data && data.fun) {
                    data.fun.call(this, node)
                  }
                }
              }
            })
          },
          onClose: function () {
            $(this).window('destroy')
          }
        }).window('open')
      })
    })
  },

  createEditor: function (select) {
    return KindEditor.create(select, E3.kingEditorParams)
  },

  /**
   * 创建一个窗口，关闭窗口后销毁该窗口对象。<br/>
   *
   * 默认：<br/>
   * width : 80% <br/>
   * height : 80% <br/>
   * title : (空字符串) <br/>
   *
   * 参数：<br/>
   * width : <br/>
   * height : <br/>
   * title : <br/>
   * url : 必填参数 <br/>
   * onLoad : function 加载完窗口内容后执行<br/>
   */
  createWindow: function (params) {
    $('<div>').css({padding: '5px'}).window({
      width: params.width ? params.width : '80%',
      height: params.height ? params.height : '80%',
      title: params.title ? params.title : ' ',
      href: params.url,
      modal: true,
      onClose: function () {
        $(this).window('destroy')
      },
      onLoad: function () {
        if (params.onLoad) {
          params.onLoad.call(this)
        }
      }
    }).window('open')
  },
  // 关闭窗口
  closeWindow: function (select) {
    $(select).window('close')
  },

  closeCurrentWindow: function () {
    $('.panel-tool-close').click()
  },

  changeItemParam: function (node, formId) {
    $.getJSON('/manager/item/param/select/' + node.id, function (data) {
      if (data.status === 200 && data.data) {
        $('#' + formId + ' .params').show()
        var paramData = JSON.parse(data.data.paramData)
        var html = '<ul style="margin-left: -40px">'
        paramData.forEach(function (pd) {
          html += '<li><table>'
          html += '<tr><td colspan="2" class="group">' + pd.group + '</td></tr>'

          pd.params.forEach(function (ps) {
            html += '<tr><td class="param"><span>' + ps + '</span>: </td><td><input class="easyui-textbox" style="width: 200px;" type="text"/></td></tr>'
          })
          html += '</table></li>'
        })
        html += '</ul>'
        $('#' + formId + ' .params td').eq(1).html(html)
      } else {
        $('#' + formId + ' .params').hide()
        $('#' + formId + ' .params td').eq(1).empty()
      }
    })
  },
  /**
   * 获取商品规格参数
   * @return {string}
   */
  getItemParamData: function (select) {
    var paramJson = []
    $(select + ' .params li').each(function (i, e) {
      var trs = $(e).find('tr')
      var group = trs.eq(0).text()
      var params = []
      // 从第二个tr开始
      for (var i = 1; i < trs.length; i++) {
        var tr = trs.eq(i)
        params.push({
          'k': $.trim(tr.find('td').eq(0).find('span').text()),
          'v': $.trim(tr.find('input').val())
        })
      }
      paramJson.push({
        'group': group,
        'params': params
      })
    })
    // 把json对象转换成字符串
    paramJson = JSON.stringify(paramJson)
    return paramJson
  },
  /**
   * 获取表格选中的ids
   * @param select
   * @return {string}
   */
  getSelectionsIds: function (select) {
    var list = $(select)
    var sels = list.datagrid('getSelections')
    var ids = []
    sels.forEach(function (value) {
      ids.push(value.id)
    })
    ids = ids.join(',')
    return ids
  },
  /**
   * 获取表格选中的categoryId
   * @param select
   * @return {*}
   */
  getSelectionsCategoryId: function (select) {
    var list = $(select)
    var sels = list.datagrid('getSelections')
    return sels[0].categoryId
  },

  /**
   * 初始化单图片上传组件 <br/>
   * 选择器为：.onePicUpload <br/>
   * 上传完成后会设置input内容以及在input后面追加<img>
   */
  initOnePicUpload: function () {
    $('.onePicUpload').click(function () {
      var _self = $(this)
      KindEditor.editor(E3.kingEditorParams).loadPlugin('image', function () {
        this.plugin.imageDialog({
          showRemote: false,
          clickFn: function (url, title, width, height, border, align) {
            var input = _self.siblings('input')
            input.parent().find('img').remove()
            input.val(url)
            input.after('<a href="' + url + '" target="_blank"><img src="' + url + '" width="80" height="50"></a>')
            this.hideDialog()
          }
        })
      })
    })
  },

  // 格式化连接
  formatUrl: function (val, row) {
    if (val) {
      return '<a href="' + val + '" target="_blank">查看</a>'
    }
    return ''
  },
  // 格式化价格
  formatPrice: function (val, row) {
    return (val / 100).toFixed(2)
  },
  // 格式化商品的状态
  formatItemStatus: function formatStatus(val, row) {
    if (val === 1) {
      return '<span style="color:green;">正常</span>'
    } else if (val === 2) {
      return '<span style="color:red;">下架</span>'
    } else {
      return '<span style="color:gray;">删除</span>'
    }
  },
  // 格式化商品规格参数
  formatItemParamData: function (val, row) {
    var json = JSON.parse(val)
    var array = []
    $.each(json, function (i, e) {
      array.push(e.group)
    })
    return array.join(',')
  }
}