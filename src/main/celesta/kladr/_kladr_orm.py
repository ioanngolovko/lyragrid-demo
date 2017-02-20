# coding=UTF-8
# Source grain parameters: version=1.0, len=353, crc32=C7BB4184; compiler=9.
"""
THIS MODULE IS BEING CREATED AUTOMATICALLY EVERY TIME CELESTA STARTS.
DO NOT MODIFY IT AS YOUR CHANGES WILL BE LOST.
"""
import ru.curs.celesta.dbutils.Cursor as Cursor
import ru.curs.celesta.dbutils.ViewCursor as ViewCursor
import ru.curs.celesta.dbutils.ReadOnlyTableCursor as ReadOnlyTableCursor
from java.lang import Object
from jarray import array
from java.util import Calendar, GregorianCalendar
from java.sql import Timestamp
import datetime

def _to_timestamp(d):
    if isinstance(d, datetime.datetime):
        calendar = GregorianCalendar()
        calendar.set(d.year, d.month - 1, d.day, d.hour, d.minute, d.second)
        ts = Timestamp(calendar.getTimeInMillis())
        ts.setNanos(d.microsecond * 1000)
        return ts
    else:
        return d

class streetCursor(Cursor):
    onPreDelete  = []
    onPostDelete = []
    onPreInsert  = []
    onPostInsert = []
    onPreUpdate  = []
    onPostUpdate = []
    def __init__(self, context):
        Cursor.__init__(self, context)
        self.name = None
        self.socr = None
        self.code = None
        self.postalcode = None
        self.gninmb = None
        self.uno = None
        self.ocatd = None
        self.context = context
    def _grainName(self):
        return 'kladr'
    def _tableName(self):
        return 'street'
    def _parseResult(self, rs):
        self.name = rs.getString('name')
        if rs.wasNull():
            self.name = None
        self.socr = rs.getString('socr')
        if rs.wasNull():
            self.socr = None
        self.code = rs.getString('code')
        if rs.wasNull():
            self.code = None
        self.postalcode = rs.getString('postalcode')
        if rs.wasNull():
            self.postalcode = None
        self.gninmb = rs.getString('gninmb')
        if rs.wasNull():
            self.gninmb = None
        self.uno = rs.getString('uno')
        if rs.wasNull():
            self.uno = None
        self.ocatd = rs.getString('ocatd')
        if rs.wasNull():
            self.ocatd = None
    def _setFieldValue(self, name, value):
        setattr(self, name, value)
    def _clearBuffer(self, withKeys):
        if withKeys:
            self.code = None
        self.name = None
        self.socr = None
        self.postalcode = None
        self.gninmb = None
        self.uno = None
        self.ocatd = None
    def _currentKeyValues(self):
        return array([None if self.code == None else unicode(self.code)], Object)
    def _currentValues(self):
        return array([None if self.name == None else unicode(self.name), None if self.socr == None else unicode(self.socr), None if self.code == None else unicode(self.code), None if self.postalcode == None else unicode(self.postalcode), None if self.gninmb == None else unicode(self.gninmb), None if self.uno == None else unicode(self.uno), None if self.ocatd == None else unicode(self.ocatd)], Object)
    def _setAutoIncrement(self, val):
        pass
    def _preDelete(self):
        for f in streetCursor.onPreDelete:
            f(self)
    def _postDelete(self):
        for f in streetCursor.onPostDelete:
            f(self)
    def _preInsert(self):
        for f in streetCursor.onPreInsert:
            f(self)
    def _postInsert(self):
        for f in streetCursor.onPostInsert:
            f(self)
    def _preUpdate(self):
        for f in streetCursor.onPreUpdate:
            f(self)
    def _postUpdate(self):
        for f in streetCursor.onPostUpdate:
            f(self)
    def _getBufferCopy(self, context):
        result = streetCursor(context)
        result.copyFieldsFrom(self)
        return result
    def copyFieldsFrom(self, c):
        self.name = c.name
        self.socr = c.socr
        self.code = c.code
        self.postalcode = c.postalcode
        self.gninmb = c.gninmb
        self.uno = c.uno
        self.ocatd = c.ocatd
    def iterate(self):
        if self.tryFindSet():
            while True:
                yield self
                if not self.nextInSet():
                    break

