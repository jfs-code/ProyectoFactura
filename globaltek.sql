-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 26-01-2023 a las 04:28:30
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `globaltek`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles`
--

CREATE TABLE `detalles` (
  `iddetalle` int(11) NOT NULL,
  `idfactura` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `preciounitario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalles`
--

INSERT INTO `detalles` (`iddetalle`, `idfactura`, `idproducto`, `cantidad`, `preciounitario`) VALUES
(1, 1, 1, 1, 50000),
(2, 1, 2, 2, 25000),
(3, 2, 3, 5, 20000),
(4, 2, 4, 4, 25000),
(5, 4, 5, 1, 100000),
(6, 4, 6, 1, 200000),
(9, 6, 5, 1, 150000),
(10, 6, 5, 3, 80000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `idfactura` int(11) NOT NULL,
  `numerofactura` varchar(10) NOT NULL,
  `fecha` varchar(10) NOT NULL,
  `tipodepago` varchar(10) NOT NULL,
  `documentocliente` varchar(15) NOT NULL,
  `nombrecliente` varchar(70) NOT NULL,
  `subtotal` int(11) NOT NULL,
  `descuento` int(11) NOT NULL,
  `iva` int(11) NOT NULL,
  `totaldescuento` decimal(9,2) NOT NULL,
  `totalimpuesto` decimal(9,2) NOT NULL,
  `total` decimal(9,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `facturas`
--

INSERT INTO `facturas` (`idfactura`, `numerofactura`, `fecha`, `tipodepago`, `documentocliente`, `nombrecliente`, `subtotal`, `descuento`, `iva`, `totaldescuento`, `totalimpuesto`, `total`) VALUES
(1, '1001', '9/12/2018', 'Contado', '80225444', 'Juan Pérez', 100000, 0, 19, '0.00', '19000.00', '119000.00'),
(2, '1002', '10/12/2018', 'Crédito', '80225777', 'Pedro Hernández', 200000, 10, 19, '20000.00', '34200.00', '214200.00'),
(4, '1003', '25/01/2023', 'Contado', '80225666', 'Mario Duarte', 300000, 5, 19, '15000.00', '54150.00', '339150.00'),
(6, '1004', '25/01/2023', 'seleccione', '345', 'ttt', 390000, 5, 19, '19500.00', '70395.00', '440895.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `idproducto` int(11) NOT NULL,
  `producto` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`idproducto`, `producto`) VALUES
(1, 'Camisa'),
(2, 'Pantalon'),
(3, 'Zapatos'),
(4, 'Tenis	'),
(5, 'Falda	'),
(6, 'Blusa');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detalles`
--
ALTER TABLE `detalles`
  ADD PRIMARY KEY (`iddetalle`),
  ADD KEY `idfactura` (`idfactura`),
  ADD KEY `idproducto` (`idproducto`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`idfactura`),
  ADD UNIQUE KEY `numerofactura` (`numerofactura`,`documentocliente`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`idproducto`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalles`
--
ALTER TABLE `detalles`
  MODIFY `iddetalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `facturas`
--
ALTER TABLE `facturas`
  MODIFY `idfactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `idproducto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalles`
--
ALTER TABLE `detalles`
  ADD CONSTRAINT `detalles_ibfk_1` FOREIGN KEY (`idfactura`) REFERENCES `facturas` (`idfactura`),
  ADD CONSTRAINT `detalles_ibfk_2` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`idproducto`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
