/* **********************************************************************************************
   DESCRIPCIÓN: Creacion de la usuario sqlserver, la base de datos y la tabla Human para el registro de adn de los humanos
   AUTOR: Angie Viviana Galindo
   FECHA: 13-02-2022
   MOTOR : SQLSERVER
*********************************************************************************************** */
USE [master];
GO

CREATE LOGIN sqlserver 
    WITH PASSWORD    = N'sqlserver',
    CHECK_POLICY     = OFF,
    CHECK_EXPIRATION = OFF;
GO
EXEC sp_addsrvrolemember 
    @loginame = N'sqlserver', 
    @rolename = N'sysadmin';




CREATE DATABASE [DetectorMutantes]
GO


USE [DetectorMutantes]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

if not exists (select * from sysobjects where name='human' and xtype='U')
   CREATE TABLE [dbo].[human](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[adnsequence] [varchar](255) NOT NULL UNIQUE,
	[is_mutant] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_ID_HUMAN] UNIQUE NONCLUSTERED 
(
	[adnsequence] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO



