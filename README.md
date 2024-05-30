# Java Testing Project

## Overview

This Java testing project is designed to ensure the reliability and functionality of our application through a series of unit and integration tests. The project is structured to support different environments, allowing you to test the application under various configurations.

## Project Structure

The project is organized into the following directories:

- **dto**: Contains Data Transfer Objects used for passing data between different layers of the application.
- **pageObject**: Contains Page Object Model (POM) classes which are used to model the pages of a web application for testing.
- **tests**: Contains all the test cases. This directory includes 9 tests that validate various functionalities of the application.
- **utils**: Contains utility classes and methods that support the testing process.
- **resources**: Contains configuration files for different environments.

### Tests

The tests are located in the tests directory under the package com.PetStore.tests. 

The `OrderTests` class includes various tests related to order functionalities in the PetStore application. Below is an overview of each test:

- **testCreateOrder**: Tests the creation of a new order and validates the response against the request.
- **testCreateEmptyOrder**: Tests the creation of an empty order and expects a 400 status code with appropriate error messages.
- **testGetOrderById**: Tests fetching an order by its ID and validates the fetched order against the original request.
- **testGetOrderNotFound**: Tests fetching an order by a non-existent ID and expects a 404 status code with appropriate error messages.
- **testDeleteOrderById**: Tests deleting an order by its ID and validates the deletion response.
- **testDeleteOrderNotFound**: Tests deleting an order by a non-existent ID and expects a 404 status code with appropriate error messages.

The `InventoryTests` class includes various tests related to inventory functionalities in the PetStore application. Below is an overview of each test:

- **testStatusCode**: Validates that the status code of the inventory API response is 200.
- **testResponseHeaders**: Validates the response headers against the expected values defined in `headers.properties`.
- **testGetInventory**: Validates that the inventory fields have non-negative integer values and ensures the integrity of the inventory data.
### Resources

The `resources` directory includes environment-specific properties files:

- **dev.properties**: Configuration for the development environment.
- **header.properties**: Contains common headers used in the tests.

## Running Tests

Tests can be executed using Maven. The environment for the tests can be specified via the command line. By default, the `dev` environment configuration is used.

To run the tests, use the following command:

```bash
mvn test -Denv=dev
