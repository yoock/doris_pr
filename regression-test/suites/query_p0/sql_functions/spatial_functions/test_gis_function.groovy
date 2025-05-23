// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
suite("test_gis_function", "arrow_flight_sql") {
    sql "set batch_size = 4096;"

    qt_sql "SELECT ST_AsText(ST_Point(24.7, 56.7));"
    qt_sql "SELECT ST_AsWKT(ST_Point(24.7, 56.7));"

    qt_sql "SELECT ST_AsText(ST_Circle(111, 64, 10000));"

    qt_sql "SELECT ST_Contains(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(5, 5));"
    qt_sql "SELECT ST_Contains(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(50, 50));"

    qt_sql "SELECT ST_Intersects(ST_Point(0, 0), ST_Point(0, 0));"
    qt_sql "SELECT ST_Intersects(ST_Point(0, 0), ST_Point(5, 5));"

    qt_sql "SELECT ST_Intersects(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_Point(0, 0));"
    qt_sql "SELECT ST_Intersects(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_Point(2, 0));"
    qt_sql "SELECT ST_Intersects(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_Point(0, 1));"

    qt_sql "SELECT ST_Intersects(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_LineFromText(\"LINESTRING (2 0, 3 0)\"));"
    qt_sql "SELECT ST_Intersects(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_LineFromText(\"LINESTRING (3 0, 4 0)\"));"
    qt_sql "SELECT ST_Intersects(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_LineFromText(\"LINESTRING (1 0, 4 0)\"));"

    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(0, 0));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(5, 0));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(5, 5));"

    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (20 0, 0 20)\"));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (-20 0, 20 0)\"));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (3 5, 8 5)\"));"

    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((5 0, 15 0, 15 10, 5 10, 5 0))\"));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((10 0, 10 10, 20 10, 20 0, 10 0))\"));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((11 0, 11 10, 21 10, 21 0, 11 0))\"));"
    qt_sql "SELECT ST_Intersects(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((10 10, 20 10, 20 20, 10 20, 10 10))\"));"

    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Point(2, 1));" 
    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Point(1, 1));"
    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Point(3, 1));"  

    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_LineFromText(\"LINESTRING (2 0, 2 2)\"));"
    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_LineFromText(\"LINESTRING (1.7 0, 1.7 2)\"));"
    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_LineFromText(\"LINESTRING (1 0.5, 1 1.5)\"));"

    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"
    qt_sql "SELECT ST_Intersects(ST_Circle(5, 5, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"
    qt_sql "SELECT ST_Intersects(ST_Circle(2, 1, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"
    qt_sql "SELECT ST_Intersects(ST_Circle(0, 1, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"

    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Circle(3, 1, 1));" 
    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Circle(2, 1, 1));"
    qt_sql "SELECT ST_Intersects(ST_Circle(1, 1, 1), ST_Circle(4, 1, 1));"

    qt_sql "SELECT ST_Touches(ST_Point(0, 0), ST_Point(0, 0));"
    qt_sql "SELECT ST_Touches(ST_Point(0, 0), ST_Point(5, 5));"

    qt_sql "SELECT ST_Touches(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_Point(0, 0));" 
    qt_sql "SELECT ST_Touches(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_Point(2, 0));"
    qt_sql "SELECT ST_Touches(ST_LineFromText(\"LINESTRING (-2 0, 2 0)\"), ST_Point(0, 1));"

    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(0, 0));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(5, 0));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Point(5, 5));"

    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (20 0, 0 20)\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (-20 0, 20 0)\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (3 5, 8 5)\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (-3 5, 8 5)\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_LineFromText(\"LINESTRING (-3 5, 15 5)\"));"

    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((5 0, 15 0, 15 10, 5 10, 5 0))\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((10 0, 10 10, 20 10, 20 0, 10 0))\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((11 0, 11 10, 21 10, 21 0, 11 0))\"));"
    qt_sql "SELECT ST_Touches(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"), ST_Polygon(\"POLYGON ((10 10, 20 10, 20 20, 10 20, 10 10))\"));"

    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Point(2, 1));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Point(1, 2));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Point(1, 1));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Point(3, 1));"

    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_LineFromText(\"LINESTRING (2 0, 2 2)\"));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_LineFromText(\"LINESTRING (1.7 0, 1.7 2)\"));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_LineFromText(\"LINESTRING (1 0.5, 1 1.5)\"));"

    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));" 
    qt_sql "SELECT ST_Touches(ST_Circle(5, 5, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"
    qt_sql "SELECT ST_Touches(ST_Circle(2, 1, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"
    qt_sql "SELECT ST_Touches(ST_Circle(0, 1, 1), ST_Polygon(\"POLYGON ((2 0, 12 0, 12 10, 2 10, 2 0))\"));"

    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Circle(3, 1, 1));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Circle(2, 1, 1));"
    qt_sql "SELECT ST_Touches(ST_Circle(1, 1, 1), ST_Circle(4, 1, 1));"

    qt_sql "SELECT ST_DISTANCE_SPHERE(116.35620117, 39.939093, 116.4274406433, 39.9020987219);"

    qt_sql "SELECT ST_ANGLE_SPHERE(116.35620117, 39.939093, 116.4274406433, 39.9020987219);"
    qt_sql "SELECT ST_ANGLE_SPHERE(0, 0, 45, 0);"

    qt_sql "SELECT ST_AsText(ST_GeometryFromText(\"LINESTRING (1 1, 2 2)\"));"
    qt_sql "SELECT ST_AsText(ST_GeomFromText(\"LINESTRING (1 1, 2 2)\"));"

    qt_sql "SELECT ST_AsText(ST_LineFromText(\"LINESTRING (1 1, 2 2)\"));"
    qt_sql "SELECT ST_AsText(ST_LineStringFromText(\"LINESTRING (1 1, 2 2)\"));"

    qt_sql "SELECT ST_AsText(ST_Point(24.7, 56.7));"

    qt_sql "SELECT ST_AsText(ST_Polygon(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"));"
    qt_sql "SELECT ST_AsText(ST_PolyFromText(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"));"
    qt_sql "SELECT ST_AsText(ST_PolygonFromText(\"POLYGON ((0 0, 10 0, 10 10, 0 10, 0 0))\"));"

    qt_sql "SELECT ST_X(ST_Point(1, 2));"
    qt_sql "SELECT ST_X(ST_Point(24.7, 56.7));"
    qt_sql "SELECT ST_Y(ST_Point(2, 1));"
    qt_sql "SELECT ST_Y(ST_Point(24.7, 56.7));"

    qt_sql "SELECT ST_Area_Square_Meters(ST_Circle(0, 0, 1));"
    qt_sql "SELECT ST_Area_Square_Km(ST_Circle(0, 0, 1));"
    qt_sql "SELECT ST_Area_Square_Meters(ST_Polygon(\"POLYGON ((0 0, 1 0, 1 1, 0 1, 0 0))\"));"
    qt_sql "SELECT ST_Area_Square_Km(ST_Polygon(\"POLYGON ((0 0, 1 0, 1 1, 0 1, 0 0))\"));"

    qt_sql "SELECT ST_Angle(ST_Point(1, 0),ST_Point(0, 0),ST_Point(0, 1));"
    qt_sql "SELECT ST_Angle(ST_Point(0, 0),ST_Point(1, 0),ST_Point(0, 1));"
    qt_sql "SELECT ST_Angle(ST_Point(1, 0),ST_Point(0, 0),ST_Point(1, 0));"
    qt_sql "SELECT ST_Angle(ST_Point(1, 0),ST_Point(-30, 0),ST_Point(150, 0));"

    qt_sql "SELECT St_Azimuth(ST_Point(1, 0),ST_Point(0, 0));"
    qt_sql "SELECT St_Azimuth(ST_Point(0, 0),ST_Point(1, 0));"
    qt_sql "SELECT St_Azimuth(ST_Point(0, 0),ST_Point(0, 1));"
    qt_sql "SELECT St_Azimuth(ST_Point(-30, 0),ST_Point(150, 0));"

    qt_sql "SELECT ST_AsText(ST_GeometryFromWKB(ST_AsBinary(ST_Point(24.7, 56.7))));"
    qt_sql "SELECT ST_AsText(ST_GeometryFromWKB(ST_AsBinary(ST_GeometryFromText(\"LINESTRING (1 1, 2 2)\"))));"
    qt_sql "SELECT ST_AsText(ST_GeometryFromWKB(ST_AsBinary(ST_Polygon(\"POLYGON ((114.104486 22.547119,114.093758 22.547753,114.096504 22.532057,114.104229 22.539826,114.106203 22.542680,114.104486 22.547119))\"))));"

    qt_sql "SELECT ST_AsText(ST_GeomFromWKB(ST_AsBinary(ST_Point(24.7, 56.7))));"
    qt_sql "SELECT ST_AsText(ST_GeomFromWKB(ST_AsBinary(ST_GeometryFromText(\"LINESTRING (1 1, 2 2)\"))));"
    qt_sql "SELECT ST_AsText(ST_GeomFromWKB(ST_AsBinary(ST_Polygon(\"POLYGON ((114.104486 22.547119,114.093758 22.547753,114.096504 22.532057,114.104229 22.539826,114.106203 22.542680,114.104486 22.547119))\"))));"

    qt_sql "SELECT ST_AsText(ST_GeometryFromWKB('01010000208A11000068270210774C5D40B8DECA334C3B4240'));"
    qt_sql "SELECT ST_X(ST_GeometryFromWKB(ST_AsBinary(ST_Point(1, 1))));"
    qt_sql "SELECT ST_AsText(ST_GeometryFromWKB(ST_AsBinary(ST_Point(1.23456789, 2.34567891))));"
    qt_sql "SELECT ST_X(ST_Point(0.9999999999999, 1));"
    qt_sql "SELECT ST_Y(ST_Point(1, 1.0000000000001));"

}
