#   coding=utf-8
from _kladr_orm import streetCursor
from ru.curs.celesta.dbutils import GridDriver


def getCursor(context):
    c = streetCursor(context)
    # c.setFilter('code', "..'01000001029003100'")
    c.orderBy('name')
    #c.orderBy('socr')
    #c.setRange('code', '11017002000009299')
    #c.setFilter('name', "'Высотная'..")
    #c.setFilter('name', "..'1+'")
    print 'get cursor '
    return c

def getGridDriver(context, callback):
    c = getCursor(context)
    #c.setRange('name')
    return GridDriver(c, callback)    

def getRows(context, gd, position, direction, tm):
    c = getCursor(context)
    gd.setPosition(position, c)
    for i in range(0, 10):
        vals = c._currentValues()
        j = 0
        for val in vals:
            tm.setValueAt(val, i, j)
            j += 1
        if not c.next():
            break

    tm.setRowCount(i+1);
    return ord

def position(context, gd, param, tm):
    c = getCursor(context)
    c.name = param
    c.navigate('=<-')
    gd.setPosition(c)
    for i in range(0, 10):
        vals = c._currentValues()
        j = 0
        for val in vals:
            tm.setValueAt(val, i, j)
            j += 1
        c.next()