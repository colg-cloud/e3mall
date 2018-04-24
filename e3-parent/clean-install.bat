echo off

::echo 输入打包项目名称，请提前配置好打包项目路径
  
::set /p project=   
  
::echo 您输入了项目名称为：%project%
    
::echo 请检查输入的项目名称"%project%" 是否正确，y/n继续执行...
  
::set /p flag=


::if %flag% == y (
	cls
	echo [INFO]开始clean... e3-paremt, e3-common, e3-manager, e3-content, e3-search, e3-sso, e3-cart, e3-order

::	cd %project%
	call mvn clean install
	echo.
	
	cd ../e3-common
	call mvn clean install
	echo.
	
	cd ../e3-manager
	call mvn clean install
	echo.
	
	cd ../e3-content
	call mvn clean install
	echo.
	
	cd ../e3-search
	call mvn clean install
	echo.
	
	cd ../e3-sso
	call mvn clean install
	echo.

	cd ../e3-cart
	call mvn clean install
	echo.

	cd ../e3-order
	call mvn clean install
	echo.

	cd ../e3-parent

	echo [INFO]结束打包... e3-paremt, e3-common, e3-manager, e3-content, e3-search, e3-sso, e3-cart, e3-order
::) else (
::	echo [INFO]你选择取消，按任意键退出
::)
::pause
::exit