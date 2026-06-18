@echo off
chcp 65001 >nul
echo ========================================
echo    智慧学工系统 - 后端启动
echo ========================================
echo.

cd /d "%~dp0"

echo [1/1] 启动 Spring Boot 后端...
echo.
echo 请稍候，正在启动...
echo 启动成功后访问：http://localhost:8080
echo.
echo 按 Ctrl+C 可以停止服务器
echo ========================================
echo.

call mvn spring-boot:run

if errorlevel 1 (
    echo.
    echo ❌ 启动失败！
    echo 请检查：
    echo   1. JDK 21 是否已安装
    echo   2. MySQL 是否正在运行
    echo   3. 数据库 student_system 是否已创建
)

pause
