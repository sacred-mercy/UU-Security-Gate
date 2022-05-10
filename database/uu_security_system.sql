-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 10, 2022 at 07:56 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uu_security_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` smallint(2) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'gaurav', 'gaurav'),
(2, 'g', 'g');

-- --------------------------------------------------------

--
-- Table structure for table `faculty_details`
--

CREATE TABLE `faculty_details` (
  `faculty_id` varchar(14) NOT NULL,
  `faculty_name` varchar(30) NOT NULL,
  `faculty_department` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `faculty_details`
--

INSERT INTO `faculty_details` (`faculty_id`, `faculty_name`, `faculty_department`) VALUES
('UU202210000011', 'Gaurav Singh', 'UIM_CA'),
('UU202210000012', 'Nikhil Parki', 'LAW'),
('UU202210000013', 'Gaurav Sharma', 'HM'),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS'),
('UU202210000023', 'Rahul Rawat', 'UIM_MGT'),
('UU202210000024', 'Rohan Negi', 'UIT_CIV');

-- --------------------------------------------------------

--
-- Table structure for table `faculty_record`
--

CREATE TABLE `faculty_record` (
  `id` varchar(14) NOT NULL,
  `name` varchar(50) NOT NULL,
  `department` text NOT NULL,
  `time` time NOT NULL,
  `date` date NOT NULL,
  `vehicleNo` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `faculty_record`
--

INSERT INTO `faculty_record` (`id`, `name`, `department`, `time`, `date`, `vehicleNo`) VALUES
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '19:37:00', '2022-04-24', 'DM8339'),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '19:44:00', '2022-04-24', ''),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '19:44:00', '2022-04-24', ''),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '20:17:21', '2022-04-24', ''),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '20:22:28', '2022-04-24', ''),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '20:33:06', '2022-04-24', ''),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '22:41:27', '2022-04-26', ''),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '22:41:53', '2022-04-26', ' '),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '22:55:08', '2022-04-26', ''),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '22:55:35', '2022-04-26', ' '),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '09:38:33', '2022-04-27', ''),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '09:51:41', '2022-04-28', 'UK07DM8339'),
('UU202210000012', 'Nikhil Parki', 'LAW', '22:32:57', '2022-04-29', 'UK07DF4372'),
('UU202210000013', 'Gaurav Sharma', 'HM', '22:34:30', '2022-04-29', 'UK06AS3997'),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '22:34:59', '2022-04-29', ' '),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '22:44:12', '2022-04-29', ''),
('UU202210000012', 'Nikhil Parki', 'LAW', '22:44:26', '2022-04-29', ''),
('UU202210000022', 'Ramesh chauhan', 'UIT_CS', '23:21:28', '2022-04-29', ''),
('UU202210000013', 'Gaurav Sharma', 'HM', '21:46:57', '2022-04-30', ''),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '12:03:51', '2022-05-01', ''),
('UU202210000012', 'Nikhil Parki', 'LAW', '12:40:07', '2022-05-05', 'UK07DM8447'),
('UU202210000013', 'Gaurav Sharma', 'HM', '14:27:32', '2022-05-06', 'UK07DM8554'),
('UU202210000011', 'Gaurav Singh', 'UIM_CA', '18:45:18', '2022-05-08', ''),
('UU202210000012', 'Nikhil Parki', 'LAW', '18:45:41', '2022-05-08', 'UK09DH6754'),
('UU202210000012', 'Nikhil Parki', 'LAW', '10:10:00', '2022-05-09', '');

-- --------------------------------------------------------

--
-- Table structure for table `visitor_records`
--

CREATE TABLE `visitor_records` (
  `name` text NOT NULL,
  `mobileNo` bigint(20) NOT NULL,
  `vehicleNo` varchar(12) DEFAULT NULL,
  `reason` text NOT NULL,
  `time` time NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `visitor_records`
--

INSERT INTO `visitor_records` (`name`, `mobileNo`, `vehicleNo`, `reason`, `time`, `date`) VALUES
('Gaurav Singh', 9368967089, 'UK07DM8339', 'visit', '10:19:24', '2022-04-26'),
('Anubhav Bisht', 9368967099, 'uk08hs7777', 'Library', '18:10:50', '2022-04-30'),
('Rajendra Singh', 2342342343, '', 'Admission', '20:36:22', '2022-04-30'),
('Rahul', 2834234234, '', 'Meeting', '21:47:38', '2022-04-30'),
('Rohan', 9368967908, 'UK08DM4444', 'admission', '13:31:40', '2022-05-05'),
('Charu', 9923872983, '', 'visit', '14:28:56', '2022-05-06');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `faculty_details`
--
ALTER TABLE `faculty_details`
  ADD PRIMARY KEY (`faculty_id`),
  ADD UNIQUE KEY `faculty_name` (`faculty_name`),
  ADD UNIQUE KEY `faculty_department` (`faculty_department`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` smallint(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
