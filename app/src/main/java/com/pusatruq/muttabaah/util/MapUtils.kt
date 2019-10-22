package com.pusatruq.muttabaah.util


object MapUtils{
    fun makeURL(sourcelat: Double, sourcelog: Double, destlat: Double, destlog: Double): String {
        val urlString = StringBuilder()
        urlString.append("https://maps.googleapis.com/maps/api/directions/json")
        urlString.append("?origin=")// from
        urlString.append(java.lang.Double.toString(sourcelat))
        urlString.append(",")
        urlString
            .append(java.lang.Double.toString(sourcelog))
        urlString.append("&destination=")// to
        urlString
            .append(java.lang.Double.toString(destlat))
        urlString.append(",")
        urlString.append(java.lang.Double.toString(destlog))
        urlString.append("&sensor=false&mode=driving&alternatives=true")
        urlString.append("&key=AIzaSyBlibNeOVRywuN2OhvAxI3BJz0xK_eeYYg")
        return urlString.toString()
    }

    fun distanceBetweenTwoPoint(
        srcLat: Double,
        srcLng: Double,
        desLat: Double,
        desLng: Double
    ): Double {
        val earthRadius = 3958.75
        val dLat = Math.toRadians(desLat - srcLat)
        val dLng = Math.toRadians(desLng - srcLng)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + (Math.cos(Math.toRadians(srcLat))
                * Math.cos(Math.toRadians(desLat)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val dist = earthRadius * c

        val meterConversion = 1609.0

        return (dist * meterConversion).toInt().toDouble()
    }

}