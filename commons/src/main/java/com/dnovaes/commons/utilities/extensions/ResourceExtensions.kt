package com.dnovaes.commons.utilities.extensions

import android.content.res.Resources
import android.content.res.XmlResourceParser
import android.util.AttributeSet
import android.util.Xml
import java.io.IOException
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

fun Resources.getAttributesFromXML(
    resourceXml: Int,
    targetElementName: String
): AttributeSet? {
    var attrSet: AttributeSet? = null
    val parser: XmlResourceParser = getLayout(resourceXml)
    var state = 0
    do {
        try {
            state = parser.next()
        } catch (e1: XmlPullParserException) {
            e1.printStackTrace()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        if (state == XmlPullParser.START_TAG) {
            if (parser.name == targetElementName) {
                attrSet = Xml.asAttributeSet(parser)
                break
            }
        }
    } while (state != XmlPullParser.END_DOCUMENT)
    return attrSet
}

