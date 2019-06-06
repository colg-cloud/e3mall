/**
 * 格式化日期
 * @param format
 * @return {void | string}
 */
Date.prototype.format = function (format) {
  const o = {
    // month
    'M+': this.getMonth() + 1,
    // day
    'd+': this.getDate(),
    // hour
    'h+': this.getHours(),
    // minute
    'm+': this.getMinutes(),
    // second
    's+': this.getSeconds(),
    // quarter
    'q+': Math.floor((this.getMonth() + 3) / 3),
    // millisecond
    'S': this.getMilliseconds()
  }
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (let k in o) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length))
    }
  }
  return format
}

/**
 * 定义E3对象
 * @type {{init: E3.init, closeCurrentWindow: E3.closeCurrentWindow, formatUrl: (function(*): string), initItemCat: E3.initItemCat, kingEditorParams: {filePostName: string, uploadJson: string, dir: string}, initOnePicUpload: E3.initOnePicUpload, createEditor: (function(*=): select), changeItemParam: E3.changeItemParam, getSelections: (function(*=): (jQuery|*)), initPicUpload: E3.initPicUpload, createWindow: E3.createWindow, closeWindow: E3.closeWindow, formatPrice: (function(*): string), formatItemParamData: (function(*=): string), getItemParamData: (function(*): string), getSelectionsCategoryId: (function(*=): *), getSelectionsIds: (function(*=): string), formatItemStatus: E3.formatItemStatus}}
 */
const E3 = {
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
  /**
   * 初始化图片上传组件
   * @param data
   */
  initPicUpload: data => {
    $('.picFileUpload').each((i, e) => {
      let _ele = $(e)
      // 选择兄弟节点
      _ele.siblings('div.pics').remove()
      _ele.after('<div class="pics"><ul></ul></div>')
      // 回显图片
      if (data && data.pics) {
        let imgS = data.pics.split(',')
        imgS.forEach(img => {
          if ($.trim(img).length > 0) {
            _ele.siblings('.pics')
                .find('ul')
                .append(`<li>
                          <a href="${img}" target="_blank">
                            <img src="${img}" width="80" height="50" alt="">
                          </a>
                         </li>`)
          }
        })
      }
      // 给"上传图片按钮"绑定click事件
      $(e).click(function () {
        let form = $(this).parentsUntil('form').parent('form')
        // 打开图片上传窗口
        KindEditor.editor(E3.kingEditorParams).loadPlugin('multiimage', function () {
          const self = this
          self.plugin.multiImageDialog({
            clickFn: urlList => {
              let imgArray = []
              KindEditor.each(urlList, (i, data) => {
                const {url} = data
                imgArray.push(url)
                form.find('.pics ul')
                    .append(`<li>
                              <a href="${url}" target="_blank">
                                <img src="${url}" width="80" height="50" alt="">
                              </a>
                             </li>`)
              })
              form.find('[name=image]').val(imgArray.join(','))
              self.hideDialog()
            }
          })
        })
      })
    })
  },

  /**
   * 初始化选择类目组件
   * @param data
   */
  initItemCat: data => {
    $('.selectItemCat').each((i, e) => {
      let _ele = $(e)
      if (data && data.cname) {
        _ele.after('<span style="margin-left: 100px;">' + data.cname + '</span>')
      } else {
        _ele.after('<span style="margin-left: 10px;"></span>')
      }
      _ele.unbind('click').click(() => {
        $('<div>').css({padding: '5px'}).html('<ul>').window({
          width: '500',
          height: '450',
          modal: true,
          closed: true,
          iconCls: 'icon-save',
          title: '选择类目',
          onOpen: function () {
            const self = this
            $('ul', self).tree({
              url: '/manager/item/cat/list',
              animate: true,
              onClick: function (node) {
                if ($(this).tree('isLeaf', node.target)) {
                  // 填写到cid中
                  _ele.parent().find('[name=cid]').val(node.id)
                  _ele.next().text(node.text).attr('cid', node.id)
                  $(self).window('close')
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

  /**
   * 创建KindEditor编辑器
   * @param select
   * @returns {select}
   */
  createEditor: select => KindEditor.create(select, E3.kingEditorParams),

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
  createWindow: params => {
    const {width, height, title, url, onLoad} = params
    $('<div>').css({padding: '5px'}).window({
      width: width ? width : '80%',
      height: height ? height : '80%',
      title: title ? title : ' ',
      href: url,
      modal: true,
      onClose: function () {
        $(this).window('destroy')
      },
      onLoad: function () {
        if (onLoad) {
          onLoad.call(this)
        }
      }
    }).window('open')
  },

  /**
   * 关闭窗口
   * @param select
   */
  closeWindow: select => {
    $(select).window('close')
  },

  /**
   * 关闭当前窗口
   */
  closeCurrentWindow: () => {
    $('.panel-tool-close').click()
  },

  /**
   * 生成商品规格模版
   * @param node
   * @param formId
   */
  changeItemParam: (node, formId) => {
    const {id} = node
    $.getJSON(`/manager/item/param/select/${id}`, data => {
      if (data.status === 200 && data.data) {
        $('#' + formId + ' .params').show()
        let paramData = JSON.parse(data.data.paramData)
        let html = `<ul style="margin-left: -40px">`
        paramData.forEach(pd => {
          const {group, params} = pd
          html += `<li><table>`
          html += `<tr><td colspan="2" class="group">${group}</td></tr>`

          params.forEach(ps => {
            html += `<tr>
                      <td class="param"><span>${ps}</span>: </td>
                      <td><input type="text" class="easyui-textbox" style="width: 200px;"/></td>
                     </tr>`
          })
          html += `</table></li>`
        })
        html += `</ul>`
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
  getItemParamData: select => {
    let paramJson = []
    $(select + ' .params li').each((i, e) => {
      let trs = $(e).find('tr')
      let group = trs.eq(0).text()
      let params = []
      // 从第二个tr开始
      for (let i = 1; i < trs.length; i++) {
        let tr = trs.eq(i)
        params.push({
          'k': $.trim(tr.find('td').eq(0).find('span').text()),
          'v': $.trim(tr.find('input').val())
        })
      }
      paramJson.push({group, params})
    })
    // 把json对象转换成字符串
    paramJson = JSON.stringify(paramJson)
    return paramJson
  },

  /**
   * 获取表格选中的行
   * @param select
   */
  getSelections: select => {
    const $list = $(select)
    return $list.datagrid('getSelections')
  },

  /**
   * 获取表格选中的ids
   * @param select
   * @return {string}
   */
  getSelectionsIds: select => {
    const $list = $(select)
    const selS = $list.datagrid('getSelections')
    let ids = []
    selS.forEach(e => {
      ids.push(e.id)
    })
    ids = ids.join(',')
    return ids
  },

  /**
   * 获取表格选中的categoryId
   * @param select
   * @return {*}
   */
  getSelectionsCategoryId: select => {
    const $list = $(select)
    const selS = $list.datagrid('getSelections')
    return selS[0].categoryId
  },


  /**
   * 初始化单图片上传组件 <br/>
   * 选择器为：.onePicUpload <br/>
   * 上传完成后会设置input内容以及在input后面追加<img>
   */
  initOnePicUpload: () => {
    $('.onePicUpload').click(function () {
      const self = $(this)
      KindEditor.editor(E3.kingEditorParams).loadPlugin('image', function () {
        this.plugin.imageDialog({
          showRemote: false,
          clickFn: function (url, title, width, height, border, align) {
            let input = self.siblings('input')
            input.parent().find('img').remove()
            input.val(url)
            input.after(`<a href="${url}" target="_blank">
                          <img src="${url}" width="80" height="50" alt="">
                         </a>`)
            this.hideDialog()
          }
        })
      })
    })
  },

  /**
   * 格式化链接
   * @param val
   * @returns {string}
   */
  formatUrl: val => val ? '<a href="' + val + '" target="_blank">查看</a>' : '',

  /**
   * 格式化价格
   * @param val
   * @returns {string}
   */
  formatPrice: val => (val / 100).toFixed(2),

  /**
   * 格式化商品的状态
   * @param val
   * @returns {string}
   */
  formatItemStatus: val => {
    if (val === 1) {
      return '<span style="color:green;">正常</span>'
    } else if (val === 2) {
      return '<span style="color:red;">下架</span>'
    } else {
      return '<span style="color:gray;">删除</span>'
    }
  },

  /**
   * 格式化商品规格参数
   * @param val
   * @returns {string}
   */
  formatItemParamData: val => {
    const json = JSON.parse(val)
    let array = []
    json.forEach(e => {
      const {group} = e
      array.push(group)
    })
    return array.join(',')
  }
}