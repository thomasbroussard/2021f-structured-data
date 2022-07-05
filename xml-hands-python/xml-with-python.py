from lxml import etree
# read from xml
tree = etree.parse('menu.xml')
root = tree.getroot()
print(root)

elems = root.xpath("/breakfast_menu/food[starts-with(./name/text(), 'B')]")
data = [[elem.find("./name").text,
         elem.find("./price").text
         ] for elem in elems]

print(data)