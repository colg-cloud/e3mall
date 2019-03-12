$(function () {
  // 在结果中筛选
  showItemCat()

  function showItemCat() {
    $('.catlist b').click(function () {
      $(this).parent().parent().find('ul').toggle()
    })
  }
})