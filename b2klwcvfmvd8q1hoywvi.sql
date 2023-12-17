-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: b2klwcvfmvd8q1hoywvi-mysql.services.clever-cloud.com:3306
-- Generation Time: Jun 25, 2023 at 05:49 PM
-- Server version: 8.0.22-13
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `b2klwcvfmvd8q1hoywvi`
--

-- --------------------------------------------------------

--
-- Table structure for table `Listing`
--

CREATE TABLE `Listing` (
  `ListingID` int NOT NULL,
  `Username` varchar(60) NOT NULL,
  `ListingName` varchar(60) NOT NULL,
  `creationDate` date NOT NULL,
  `Picture` mediumblob,
  `Price` double NOT NULL,
  `Brand` varchar(60) NOT NULL,
  `Model` varchar(60) NOT NULL,
  `Colour` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BodyType` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Doors` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Seats` int NOT NULL,
  `Interior` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `AirBags` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `AirConditioning` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `RegistrationDate` date NOT NULL,
  `GeneralInspection` date NOT NULL,
  `Consumption` double NOT NULL,
  `Miles` int NOT NULL,
  `Owners` int DEFAULT '1',
  `EmissionClass` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `EnvironmentalBadge` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Engine` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Performance` int NOT NULL,
  `CylinderCapacity` int NOT NULL,
  `Gearbox` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FuelType` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Description` mediumtext,
  `Condition` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `FirstName` varchar(60) NOT NULL,
  `LastName` varchar(60) NOT NULL,
  `Username` varchar(60) NOT NULL,
  `Email` varchar(60) NOT NULL,
  `City` varchar(60) NOT NULL,
  `Address` varchar(60) NOT NULL,
  `Password` varchar(64) NOT NULL,
  `Salt` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Listing`
--
ALTER TABLE `Listing`
  ADD PRIMARY KEY (`ListingID`),
  ADD KEY `User2Listing` (`Username`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Listing`
--
ALTER TABLE `Listing`
  MODIFY `ListingID` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Listing`
--
ALTER TABLE `Listing`
  ADD CONSTRAINT `User2Listing` FOREIGN KEY (`Username`) REFERENCES `User` (`Username`) ON DELETE CASCADE ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
