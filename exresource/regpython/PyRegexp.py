import re
source =open(r"exresource\.sourcetmp",encoding='UTF-8').read()
regexp=r""
rs=re.compile(regexp)
result=rs.findall(source)
for var in result:
    print(var)
