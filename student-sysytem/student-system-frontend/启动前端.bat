@echo off
chcp 65001 >nul
echo ========================================
echo    智慧学工系统 - 前端启动
echo ========================================
echo.

cd /d "%~dp0"

echo [1/2] 检查依赖...
if not exist "node_modules" (
    echo 正在安装依赖（使用淘宝镜像）...
    call npm install --registry=https://registry.npmmirror.com
    if errorlevel 1 (
        echo.
        echo ❌ 依赖安装失败！
        pause
        exit /b 1
    )
    echo ✅ 依赖安装完成！
) else (
    echo ✅ 依赖已安装
)

echo.
echo [2/2] 启动前端开发服务器...
echo.
echo 请稍候，正在启动...
echo 启动成功后访问：http://localhost:3000
echo.
echo 按 Ctrl+C 可以停止服务器
echo ========================================
echo.

call npm run serve

if errorlevel 1 (
    echo.
    echo ❌ 启动失败！
    echo 尝试使用 npx 方式启动...
    call npx vue-cli-service serve
)

pause
