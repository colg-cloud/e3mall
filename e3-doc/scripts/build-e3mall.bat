@echo off
rem ----------------------------------------------------------------------------------------------------
rem  ���ű����ڱ��밲װ: e3mall
rem  ����Ҫ��: Maven + JDK8
rem  ���з�ʽ: cmd -> cd D:\workspaces\idea\iheima\e3mall\e3-doc\scripts
rem ----------------------------------------------------------------------------------------------------


cls
echo [INFO] ��ʼ���밲װ: 1. e3-parent, 2. e3-common, 3. e3-manager, 4. e3-content, 5. e3-search, 6.e3-sso

echo ">>>> 1. e3-parent"
cd ../../e3-parent
call mvn clean install

echo ">>>> 2. e3-common"
cd ../e3-common
call mvn clean install

echo ">>>> 3. e3-manager"
cd ../e3-manager
call mvn clean install

echo ">>>> 4. e3-content"
cd ../e3-content
call mvn clean install

echo ">>>> 5. e3-search"
cd ../e3-search
call mvn clean install

echo ">>>> 6. e3-sso"
cd ../e3-sso
call mvn clean install

echo [INFO] �������밲װ: 1. e3-parent, 2. e3-common, 3. e3-manager, 4. e3-content, 5. e3-search, 6.e3-sso

cd ../e3-doc/scripts

:: pause