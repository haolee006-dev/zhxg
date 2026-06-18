-- 智慧学工系统数据库脚本

CREATE DATABASE IF NOT EXISTS student_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE student_system;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像',
    role VARCHAR(20) DEFAULT 'student' COMMENT '角色：admin-管理员，teacher-教师，student-学生',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除'
) COMMENT='系统用户表';

-- 学生表
CREATE TABLE student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别',
    birthday DATE COMMENT '出生日期',
    id_card VARCHAR(18) COMMENT '身份证号',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(255) COMMENT '家庭住址',
    native_place VARCHAR(100) COMMENT '籍贯',
    nation VARCHAR(50) COMMENT '民族',
    political_status VARCHAR(50) COMMENT '政治面貌',
    college VARCHAR(100) COMMENT '学院',
    major VARCHAR(100) COMMENT '专业',
    class_name VARCHAR(50) COMMENT '班级',
    grade INT COMMENT '年级',
    education_level VARCHAR(20) COMMENT '学历层次',
    status VARCHAR(20) DEFAULT '在读' COMMENT '学籍状态',
    enrollment_date DATE COMMENT '入学日期',
    expected_graduation DATE COMMENT '预计毕业日期',
    dormitory_no VARCHAR(20) COMMENT '宿舍楼号',
    room_no VARCHAR(20) COMMENT '房间号',
    counselor_id VARCHAR(20) COMMENT '辅导员ID',
    photo VARCHAR(255) COMMENT '照片',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='学生信息表';

-- 辅导员表
CREATE TABLE counselor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_no VARCHAR(20) NOT NULL UNIQUE COMMENT '工号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    college VARCHAR(100) COMMENT '所属学院',
    office VARCHAR(100) COMMENT '办公室',
    title VARCHAR(50) COMMENT '职称',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='辅导员表';

-- 学生档案记录表
CREATE TABLE student_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    record_type VARCHAR(50) COMMENT '档案类型',
    content TEXT COMMENT '内容',
    remark VARCHAR(255) COMMENT '备注',
    operator VARCHAR(50) COMMENT '操作人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='学生档案记录表';

-- 违纪处分表
CREATE TABLE disciplinary_action (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    type VARCHAR(50) COMMENT '处分类型',
    reason TEXT COMMENT '处分原因',
    level VARCHAR(50) COMMENT '处分等级',
    action_date DATE COMMENT '处分日期',
    result VARCHAR(255) COMMENT '处分结果',
    status VARCHAR(20) DEFAULT '生效中' COMMENT '状态',
    approver VARCHAR(50) COMMENT '审批人',
    approve_date DATE COMMENT '审批日期',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='违纪处分表';

-- 迎新登记表
CREATE TABLE welcome_registration (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT COMMENT '学生ID',
    student_no VARCHAR(20) COMMENT '学号',
    name VARCHAR(50) COMMENT '姓名',
    expected_date DATE COMMENT '预计到达日期',
    transport_type VARCHAR(50) COMMENT '交通方式',
    arrival_station VARCHAR(100) COMMENT '到达站点',
    arrival_time DATETIME COMMENT '到达时间',
    luggage_count INT DEFAULT 0 COMMENT '行李数量',
    accommodation_need VARCHAR(20) COMMENT '住宿需求',
    status VARCHAR(20) DEFAULT '待确认' COMMENT '状态',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='迎新登记表';

-- 报到流程表
CREATE TABLE welcome_process (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT COMMENT '学生ID',
    process_name VARCHAR(100) COMMENT '流程名称',
    process_type VARCHAR(50) COMMENT '流程类型',
    status VARCHAR(20) DEFAULT '未完成' COMMENT '状态',
    complete_time DATETIME COMMENT '完成时间',
    operator VARCHAR(50) COMMENT '操作人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='报到流程表';

-- 学生证表
CREATE TABLE student_card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    card_type VARCHAR(50) COMMENT '证件类型',
    card_no VARCHAR(50) COMMENT '证件号码',
    issue_date DATE COMMENT '发放日期',
    expire_date DATE COMMENT '有效期至',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='学生证表';

-- 奖学金表
CREATE TABLE scholarship (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    scholarship_name VARCHAR(100) COMMENT '奖学金名称',
    scholarship_type VARCHAR(50) COMMENT '奖学金类型',
    amount DECIMAL(10,2) COMMENT '金额',
    award_date DATE COMMENT '获奖日期',
    semester VARCHAR(20) COMMENT '学期',
    status VARCHAR(20) DEFAULT '已发放' COMMENT '状态',
    approver VARCHAR(50) COMMENT '审批人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='奖学金表';

-- 资助表
CREATE TABLE financial_aid (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    aid_type VARCHAR(50) COMMENT '资助类型',
    aid_name VARCHAR(100) COMMENT '资助名称',
    amount DECIMAL(10,2) COMMENT '金额',
    apply_date DATE COMMENT '申请日期',
    approve_date DATE COMMENT '审批日期',
    status VARCHAR(20) DEFAULT '待审批' COMMENT '状态',
    approver VARCHAR(50) COMMENT '审批人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='资助表';

-- 宿舍表
CREATE TABLE dormitory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    building_no VARCHAR(20) COMMENT '楼栋号',
    room_no VARCHAR(20) COMMENT '房间号',
    floor INT COMMENT '楼层',
    capacity INT DEFAULT 4 COMMENT '容量',
    current_count INT DEFAULT 0 COMMENT '当前人数',
    room_type VARCHAR(20) COMMENT '房间类型',
    status VARCHAR(20) DEFAULT '正常' COMMENT '状态',
    building_type VARCHAR(20) COMMENT '楼栋类型',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='宿舍表';

-- 宿舍卫生检查表
CREATE TABLE dormitory_check (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dormitory_id BIGINT COMMENT '宿舍ID',
    building_no VARCHAR(20) COMMENT '楼栋号',
    room_no VARCHAR(20) COMMENT '房间号',
    check_date DATE COMMENT '检查日期',
    check_type VARCHAR(50) COMMENT '检查类型',
    score INT COMMENT '评分',
    result VARCHAR(50) COMMENT '检查结果',
    checker VARCHAR(50) COMMENT '检查人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='宿舍卫生检查表';

-- 宿舍报修表
CREATE TABLE dormitory_repair (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT COMMENT '学生ID',
    dormitory_id BIGINT COMMENT '宿舍ID',
    building_no VARCHAR(20) COMMENT '楼栋号',
    room_no VARCHAR(20) COMMENT '房间号',
    repair_type VARCHAR(50) COMMENT '报修类型',
    description TEXT COMMENT '问题描述',
    report_date DATE COMMENT '报修日期',
    status VARCHAR(20) DEFAULT '待处理' COMMENT '状态',
    complete_date DATE COMMENT '完成日期',
    handler VARCHAR(50) COMMENT '处理人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='宿舍报修表';

-- 宿舍违纪表
CREATE TABLE dormitory_violation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT COMMENT '学生ID',
    dormitory_id BIGINT COMMENT '宿舍ID',
    building_no VARCHAR(20) COMMENT '楼栋号',
    room_no VARCHAR(20) COMMENT '房间号',
    violation_type VARCHAR(50) COMMENT '违纪类型',
    description TEXT COMMENT '违纪描述',
    violation_date DATE COMMENT '违纪日期',
    punishment VARCHAR(255) COMMENT '处罚措施',
    status VARCHAR(20) DEFAULT '待处理' COMMENT '状态',
    handler VARCHAR(50) COMMENT '处理人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='宿舍违纪表';

-- 离校表
CREATE TABLE leave_school (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT COMMENT '学生ID',
    student_no VARCHAR(20) COMMENT '学号',
    name VARCHAR(50) COMMENT '姓名',
    leave_type VARCHAR(50) COMMENT '离校类型',
    expected_leave_date DATE COMMENT '预计离校日期',
    actual_leave_date DATE COMMENT '实际离校日期',
    destination VARCHAR(255) COMMENT '目的地',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    reason TEXT COMMENT '离校原因',
    status VARCHAR(20) DEFAULT '待审批' COMMENT '状态',
    approver VARCHAR(50) COMMENT '审批人',
    approve_date DATE COMMENT '审批日期',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='离校表';

-- 毕业生表
CREATE TABLE graduation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT COMMENT '学生ID',
    student_no VARCHAR(20) COMMENT '学号',
    name VARCHAR(50) COMMENT '姓名',
    college VARCHAR(100) COMMENT '学院',
    major VARCHAR(100) COMMENT '专业',
    graduation_status VARCHAR(50) COMMENT '毕业状态',
    thesis_status VARCHAR(50) COMMENT '论文状态',
    employment_status VARCHAR(50) COMMENT '就业状态',
    employment_unit VARCHAR(255) COMMENT '就业单位',
    graduation_date DATE COMMENT '毕业日期',
    certificate_no VARCHAR(50) COMMENT '证书编号',
    status VARCHAR(20) DEFAULT '在读' COMMENT '状态',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='毕业生表';

-- 健康档案表
CREATE TABLE health_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    check_date DATE COMMENT '检查日期',
    height DECIMAL(5,2) COMMENT '身高',
    weight DECIMAL(5,2) COMMENT '体重',
    vision VARCHAR(20) COMMENT '视力',
    blood_pressure VARCHAR(20) COMMENT '血压',
    health_status VARCHAR(50) COMMENT '健康状况',
    disease_history TEXT COMMENT '疾病史',
    allergy_history TEXT COMMENT '过敏史',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='健康档案表';

-- 学情测评表
CREATE TABLE student_evaluation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    semester VARCHAR(20) COMMENT '学期',
    evaluation_type VARCHAR(50) COMMENT '测评类型',
    moral_score DECIMAL(5,2) COMMENT '德育分',
    intellectual_score DECIMAL(5,2) COMMENT '智育分',
    physical_score DECIMAL(5,2) COMMENT '体育分',
    total_score DECIMAL(5,2) COMMENT '总分',
    level VARCHAR(20) COMMENT '等级',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='学情测评表';

-- 学生通报表
CREATE TABLE student_notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) COMMENT '标题',
    content TEXT COMMENT '内容',
    type VARCHAR(50) COMMENT '类型',
    sender VARCHAR(50) COMMENT '发送人',
    receiver_type VARCHAR(20) COMMENT '接收对象类型',
    receiver_id BIGINT COMMENT '接收对象ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读',
    read_time DATETIME COMMENT '阅读时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='学生通报表';

-- 假期留校表
CREATE TABLE holiday_stay (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    holiday_name VARCHAR(100) COMMENT '假期名称',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    reason TEXT COMMENT '留校原因',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    status VARCHAR(20) DEFAULT '待审批' COMMENT '状态',
    approver VARCHAR(50) COMMENT '审批人',
    remark VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
) COMMENT='假期留校表';

-- 初始化管理员账号（密码：123456，MD5加密）
INSERT INTO sys_user (username, password, real_name, role, status) VALUES 
('admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', 'admin', 1),
('teacher', 'e10adc3949ba59abbe56e057f20f883e', '测试教师', 'teacher', 1),
('student', 'e10adc3949ba59abbe56e057f20f883e', '测试学生', 'student', 1);

-- 初始化测试数据
INSERT INTO student (student_no, name, gender, birthday, id_card, phone, email, college, major, class_name, grade, status, enrollment_date) VALUES
('2024001', '张三', '男', '2000-01-15', '110101200001150011', '13800138001', 'zhangsan@example.com', '计算机学院', '软件工程', '软件2401', 2024, '在读', '2024-09-01'),
('2024002', '李四', '女', '2000-03-20', '110101200003200022', '13800138002', 'lisi@example.com', '计算机学院', '计算机科学', '计科2401', 2024, '在读', '2024-09-01'),
('2023001', '王五', '男', '1999-05-10', '110101199905100033', '13800138003', 'wangwu@example.com', '电子工程学院', '电子信息', '电信2301', 2023, '在读', '2023-09-01'),
('2023002', '赵六', '女', '1999-08-25', '110101199908250044', '13800138004', 'zhaoliu@example.com', '电子工程学院', '通信工程', '通信2301', 2023, '在读', '2023-09-01'),
('2022001', '孙七', '男', '1998-11-30', '110101199811300055', '13800138005', 'sunqi@example.com', '管理学院', '工商管理', '工商2201', 2022, '在读', '2022-09-01');

INSERT INTO counselor (employee_no, name, gender, phone, email, college, office, title) VALUES
('T001', '陈老师', '女', '13900139001', 'chen@example.com', '计算机学院', 'A101', '副教授'),
('T002', '刘老师', '男', '13900139002', 'liu@example.com', '电子工程学院', 'B201', '教授');

INSERT INTO dormitory (building_no, room_no, floor, capacity, current_count, room_type, status, building_type) VALUES
('A1', '101', 1, 4, 2, '四人间', '正常', '男生宿舍'),
('A1', '102', 1, 4, 3, '四人间', '正常', '男生宿舍'),
('A2', '201', 2, 4, 4, '四人间', '已满', '女生宿舍'),
('A2', '202', 2, 4, 1, '四人间', '正常', '女生宿舍');
