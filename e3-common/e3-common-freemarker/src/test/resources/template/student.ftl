<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>学生信息 - ${student.name}</title>
</head>
<body>
<br>
  学生信息：<br>
  学号：${student.id};
  姓名：${student.name};
  年龄：${student.age};
  家庭住址：${student.address}<br>
  学生列表：
  <table border="1" style="border-collapse: collapse">
    <tr>
      <th>序号</th>
      <th>学号</th>
      <th>姓名</th>
      <th>年龄</th>
      <th>家庭住址</th>
    </tr>
    <#list stuList as stu>
      <#if stu_index % 2 == 0>
      <tr bgcolor="red">
        <#else>
      <tr bgcolor="green">
      </#if>
        <td>${stu_index + 1}</td>
        <td>${stu.id}</td>
        <td>${stu.name}</td>
        <td>${stu.age}</td>
        <td>${stu.address!""}</td>
      </tr>
    </#list>
  </table>
  <br>
  <!-- 可以使用?date,?time,?datetime,?string(parten)-->
  当前日期: ${date?date}<br>
  当前时间: ${date?time}<br>
  当前日期时间: ${date?datetime}<br>
  当前日期时间格式化：${date?string("yyyy/MM/dd HH:mm:ss")}<br>
  null值的处理：${val!"val的值为null"}<br>
  判断val的值是否为null：<br>
  <#if val??>
    val中有内容
  <#else>
    val的值为null
  </#if>
  引用模板测试：<br>
  <#include "hello.ftl">
</body>
</html>