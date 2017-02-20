# coding: utf-8
from lyra.basicForm import form, formfield
from lyra.gridForm import GridForm

from kladr._kladr_orm import streetCursor

@form()
class StreetGrid(GridForm):
    def __init__(self, context):        
        super(StreetGrid, self).__init__(context)
        self.createAllBoundFields()
        
    def _getCursor(self, context):
        street_cursor = streetCursor(context)
        street_cursor.orderBy('name')
        #street_cursor.setFilter('name', u"'Киров'%")
        return street_cursor