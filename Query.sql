--RANDOM ID NHÂN VIÊN
GO
CREATE VIEW [dbo].[Random]
AS SELECT RAND() AS RAND
GO
CREATE FUNCTION RandomString (@len AS INT)
    RETURNS varchar(MAX)
BEGIN
    DECLARE @STR VARCHAR(4)
    DECLARE @NUM INT
    SET @STR = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    DECLARE @newstr VARCHAR(MAX)
    DECLARE @counter int
    SET @newstr = ''
    SET @counter = 0
    WHILE @counter < @len
        BEGIN
            SELECT @newstr = @newstr + SUBSTRING(@str, (SELECT CONVERT(int, (RAND * LEN(@str) + 1)) FROM Random), 1)
            SET @counter = @counter + 1
        END
    SELECT @NUM = COUNT(MATK) FROM DBO.TAIKHOAN
    SET @newstr = CONCAT(@newstr, @NUM)
    RETURN @newstr
END
GO
--đăng nhập(tài khoản, mật khẩu)
CREATE PROCEDURE LOG_IN
            @ID ID,
            @PW CHAR(8),
            @STATUS AS INT OUT
AS
    BEGIN
    SET @STATUS = 0
    IF EXISTS (SELECT 1 FROM DBO.TAIKHOAN AS TK WHERE TK.MATK = @ID AND TK.MATKHAU = @PW)
        BEGIN
            IF EXISTS(SELECT 1 FROM DBO.NHANVIEN WHERE NGAYNGHILAM = NULL AND MANV = @ID)
                BEGIN
                    SET @STATUS = 2
                end
             else
                begin
            SELECT @STATUS = TK.LOAITK FROM DBO.TAIKHOAN AS TK WHERE TK.MATK = @ID
                    end
        end
    RETURN @STATUS
     end
GO
--đăng kí(tên, tài khoản, mật khẩu, id, email, số điện thoại)
EXEC REGISTER 'NGUYEN DAC PHAT', '0364592706',
    'nk.angel07@gmail.com','160/54/1', 'sharks46',1
SELECT * FROM DBO.TAIKHOAN
CREATE PROCEDURE REGISTER
    @NAME NAME,
    @phone NAME,
    @email NAME,
    @address NAME,
    @password char(20),
    @STATUS char(10) OUT
AS
BEGIN
    DECLARE @ID ID
    SET @ID = DBO.RandomString(4)
    SET @STATUS = null
    INSERT INTO DBO.NHANVIEN(MANV,NGAYVAOLAM, NGAYNGHILAM,CHUCVU) VALUES (@ID, GETDATE(),NULL, 'NHANVIEN')
    INSERT INTO DBO.TAIKHOAN VALUES(@ID, @password, 1)
    INSERT INTO DBO.TT_CANHAN(MANV, HOTEN, DIACHI, EMAIL, SDT) VALUES (@ID, @NAME, @address, @email, @phone)
    IF(@@ROWCOUNT = 1)
        BEGIN
            SET @STATUS = @ID
        end
end
GO
--kiểm tra số cmnd
CREATE PROCEDURE CHECK_CMND
    @CMND CHAR(20),
    @STATUS INT OUT
    AS
    BEGIN
    SET @STATUS = 1
        IF EXISTS(SELECT 1 FROM DBO.TT_CANHAN AS TTCN WHERE TTCN.SOCMND = @CMND)
        BEGIN
            SET @STATUS = 0
        end
        RETURN @STATUS
        END
GO
--lấy tên nhân viên
CREATE PROC GET_NAME
    @ID ID,
    @NAME NAME OUT
    AS
    BEGIN
        SELECT @NAME = TTCN.HOTEN FROM DBO.TT_CANHAN AS TTCN WHERE TTCN.MANV = @ID
    end
GO
--lấy chức vụ nhân viên
CREATE PROC GET_CLASS
    @ID ID,
    @CLASS VARCHAR(30) OUT
    AS
    BEGIN
        SELECT @CLASS = NV.CHUCVU FROM DBO.NHANVIEN AS NV WHERE NV.MANV = @ID
    end
    GO
--xem thông tin cá nhân
CREATE PROC INFO
    @ID ID
    AS
    BEGIN
    SELECT TTCN.HOTEN, TTCN.QUEQUAN, TTCN.TRINGDOHOCVAN,
           TTCN.EMAIL, TTCN.NAMSINH, NV.CHUCVU, TTCN.SOCMND,
           TTCN.DIACHI, TTCN.SDT
           FROM DBO.TT_CANHAN AS TTCN, DBO.NHANVIEN AS NV
        WHERE TTCN.MANV = NV.MANV AND TTCN.MANV = @ID
end
GO
select * from dbo.TT_CANHAN
--xem thông tin công việc
CREATE PROC JOB
    @ID ID
    AS
    BEGIN
        SELECT TTCN.HOTEN, NV.MANV, NV.NGAYVAOLAM, NV.NGUOIQL, NV.CHUCVU, NV.PHONGBAN
        FROM DBO.TT_CANHAN AS TTCN, DBO.NHANVIEN AS NV
        WHERE NV.MANV = TTCN.MANV AND NV.MANV = @ID
    end
--đổi mật khẩu
CREATE PROC CHANGE_PW
    @ID ID,
    @OMK CHAR(20),
    @MK CHAR(20),
    @STATUS INT OUT
AS
    BEGIN
        SET @STATUS = 0
        IF EXISTS(SELECT 1 FROM DBO.TAIKHOAN AS TK WHERE TK.MATK = @ID AND
                                                         TK.MATKHAU = @OMK)
            BEGIN
                UPDATE DBO.TAIKHOAN SET MATKHAU = @MK WHERE MATK = @ID
                IF(@@ROWCOUNT = 1)
                    BEGIN
                        SET @STATUS = 1
                    end
            end
        else
        begin
            set @STATUS = 2
        end
    end
RETURN @STATUS
go
--chỉnh sửa thông tin cá nhân
CREATE PROC UPDATE_INFO
    @ID ID,
    @NAME NAME,
    @QUEQUAN NAME,
    @TRINHDO NAME,
    @EMAIL NAME,
    @NGAYSINH DATE,
    @CMND CHAR(20),
    @DIACHI NAME,
    @SDT CHAR(20),
    @STATUS INT OUT
    AS
    BEGIN
        SET @STATUS = 0
        UPDATE DBO.TT_CANHAN SET HOTEN = @NAME, QUEQUAN = @QUEQUAN, TRINGDOHOCVAN = @TRINHDO,
                                EMAIL = @EMAIL, NAMSINH = @NGAYSINH, SOCMND = @CMND, DIACHI = @DIACHI,
                                 SDT = @SDT WHERE MANV = @ID
        IF(@@ROWCOUNT = 1)
            BEGIN
                SET @STATUS = 1
            end
        RETURN @STATUS
    end

    GO
--thêm người thân
CREATE PROCEDURE ADD_RELATION
    @ID ID,
    @NAME NAME,
    @NAMSINH DATE,
    @QUANHE CHAR(10),
    @SDT CHAR(20),
    @STATUS INT OUT
    AS
    BEGIN
        SET @STATUS = 0
        INSERT INTO DBO.NGUOITHAN(MANV, TEN, NAMSINH, QUANHE, SODIENTHOAI) VALUES (@ID, @NAME, @NAMSINH, @QUANHE, @SDT)
        IF(@@ROWCOUNT = 1)
        BEGIN
            SET @STATUS = 1
        end
    RETURN @STATUS
    end
go
--xem lương xem số ngày nghĩ
--tính lương
--xem danh sách nhân viên
CREATE PROC EMPLOYEE
    AS BEGIN
    SELECT TTCN.HOTEN, NV.MANV, NV.CHUCVU, NV.NGUOIQL
    FROM DBO.NHANVIEN AS NV, DBO.TT_CANHAN AS TTCN WHERE NV.MANV = TTCN.MANV
end
GO
--xem nhân viên mình đang quản lý
CREATE PROC MY_EMPLOYEE
    @ID ID
    AS
    BEGIN
        SELECT TTCN.HOTEN, NV.MANV, NV.CHUCVU FROM DBO.NHANVIEN AS NV, DBO.TT_CANHAN AS TTCN
        WHERE NV.NGUOIQL = @ID AND NV.MANV = TTCN.MANV
    end
go
--vô hiệu hóa nhân viên
CREATE PROC DEACTIVATE
    @ID ID,
    @STATUS INT OUT
    AS
    BEGIN
        SET @STATUS = 0
        UPDATE DBO.NHANVIEN SET NGAYNGHILAM = GETDATE() WHERE MANV = @ID
        IF(@@ROWCOUNT = 1)
        BEGIN
            SET @STATUS = 1
        end
    end
    select * from dbo.nhanvien
--phân công người quản lý
CREATE PROC  ADD_MANAGEMENT
    @EID ID,
    @MID ID,
    @STATUS INT OUT
    AS BEGIN
        SET @STATUS = 0
    UPDATE DBO.NHANVIEN SET NGUOIQL = @MID WHERE MANV = @EID
    IF(@@ROWCOUNT = 1)
        BEGIN
            SET @STATUS = 1
        end
    RETURN @STATUS
end
    GO
--gửi và nhận thư từ người quản lý đến nhân viên.
CREATE PROC SEND_MESSAGE
    @RID ID,
    @SID ID,
    @MESSAGE NVARCHAR(1000),
    @STATUS INT OUT
AS BEGIN
    SET @STATUS = 0
    INSERT INTO DBO.MAIL VALUES (@RID, @MESSAGE, @SID)
    IF(@@ROWCOUNT = 1)
    BEGIN
        SET @STATUS = 1
    end
    RETURN @STATUS
end
    GO
--điểm danh
CREATE PROC CHECK_work
    @ID ID
    AS BEGIN
    INSERT INTO DBO.NGAYNGHI VALUES (@ID, GETDATE())
end
    go
    --tính lương
CREATE PROC TINHLUONG
    @ID ID,
    @SALARY MONEY OUT
    AS
    BEGIN
        DECLARE @SONN INT
        SELECT @SONN = COUNT(*) FROM DBO.NGAYNGHI WHERE MANV = @ID AND
                                                MONTH(NGAYNGHI)= MONTH(GETDATE()) AND YEAR(NGAYNGHI) = YEAR(GETDATE())
        SET @SALARY = 6000000 - @SONN*200000
        RETURN @SALARY
    end
        GO
--tính số ngày nghỉ
CREATE PROC DAYOFF
    @ID ID
    AS BEGIN
    SELECT * FROM DBO.NGAYNGHI WHERE MANV = @ID AND
        MONTH(NGAYNGHI)= MONTH(GETDATE()) AND YEAR(NGAYNGHI) = YEAR(GETDATE())
end
select * from dbo.NGAYNGHI
update dbo.TAIKHOAN set LOAITK = 3 where MATK = 'dbbb3'
select * from dbo.TAIKHOAN