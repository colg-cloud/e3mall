- solr 配置
```xml
  <!--添加fieldType-->
  <fieldType name="text_ik" class="solr.TextField">
    <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
  </fieldType>
  <!--配置业务域，设置业务系统field-->
  <field name="item_title" type="text_ik" indexed="true" stored="true"/>
  <field name="item_sell_point" type="text_ik" indexed="true" stored="true"/>
  <field name="item_price"  type="long" indexed="true" stored="true"/>
  <field name="item_image" type="string" indexed="false" stored="true" />
  <field name="item_category_name" type="string" indexed="true" stored="true" />

  <field name="item_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
  <copyField source="item_title" dest="item_keywords"/>
  <copyField source="item_sell_point" dest="item_keywords"/>
  <copyField source="item_category_name" dest="item_keywords"/>
```

