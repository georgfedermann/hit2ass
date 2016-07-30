How to work with HitAss DocFamily templates.

These are all velocity template files.
Thus, using velocity technology and the hitAssTools, these templates can be
transformed into actual DocDesign Workspaces, and from there can be transformed
into DeploymentPackages and be deployed into DocBase instances.

The basis template is the workspace. It holds all other content elements
like font definitions, paragraphs, which contain fixed texts, dynamic texts,
and so on.

The following sections describe the Template hierarchy, what values can
be configured for each template (list might be extended as new functionality 
becomes implemented), and how the templates work together to form complex 
DocDesign Workspaces.

Generally, a context element is expected, which is queried for configuration
values, like document names, or xpaths in dynamic text elements.

BoWorkspace->(BoProjectGroup,BoRegionGroup,BoModuleGroup,BoEditorConfigurationGroup,BoVariableGroup)
BoProjectGroup->BoProject
BoProject->(BoDocument,BoPage)
BoDocument->(BoPageRepetition)
BoPage->BoParagraph



TemplateWorkspace.vlt
=====================================
contains the workspace framework.
Probably maps to one HIT/CLOU base text component, i.e. a text component, that is referenced
from a calling script and can reference multiple other text components, directly or
indirectly, but is not referenced by another text component in the context of the current
document definition.
It will have nested paragraphs that manage the contents of the workspace, be it dynamic
or static contents.
Configurables:
-------------------------------------
workspaceName=HitAssWorkspace
projectsName=HitAssProjects
projectName=HitAssProject
documentName=HitAssDocument
repeatingPageName=HitAssRepeatingPage
pageContentName=HitAssPageContent


TemplateParagraph.vlt
=====================================
contains fixed texts, dynamic texts and new lines.
Probably most of the content of a HIT/CLOU document can and will be wrapped inside one single
paragraph, to keep things simple.
Configurables:
-------------------------------------
paragraphName=documentParagraph
content={a list of objects of type Text, NewLine, DynamicText, ... that will be mapped
	to specified templates and rendered as child elements of the paragraph element. Child elements
	are added to the paragraph as sequential list of <object> elements after the <Properties>
	child element.}


TemplateText.vlt
=====================================
contains fixed texts of arbitrary length. That can be a single character, a word, a sentence,
or multiple lines of text. Newlines may be encoded in fixed text, i.e. inline within a TemplateText,
or represented by the dedicated TemplateCReturn (represented by respective HitClouCommand objects
in the Baustein's AST representation).
Content will be written to object/Properties/Attribute[@name="Conten"] as a CDATA section.
Configurables:
-------------------------------------
textName=Salutation
textContent=Sehr geehrter Herr

TemplateWhileLelementFlavor.vlt
=====================================
contains the structure of the lelement flavor WHILE loop
#X< lelement
#S lelement <> "*" :
    #L indtext0 & { lelement }
    #X< lelement
#
this is by far the most common while loop in the realm.
Configurables:
-------------------------------------
inputVariable=lelement
listVariable=indtext0
flagValue=*

TemplateCReturn.vlt
=====================================
Marks the position of a line break within a paragraph.
Configurables:
-------------------------------------
linebreakName=newline

TemplateNewLineHitcommand.vlt
=============================
Will probably replace TemplateCReturn.vlt.
Is more flexible. An XPath expression hast to be given which will be
evaluated by the XSLT transformer during document processing.
Configurables:
-------------------------------------
upperLimitExpression=var:read('upperLimit')     the value is anything returned by the toXPathString() method when called on the expression

TemplateDynamicContentReference.vlt
=====================================
This element will be replaced by the value retrieved by evaluation of a specified XPath expression.
Configurables:
-------------------------------------
dynamicContentName=customerId
xpath=concat('Hello, ', 'World!')


TemplateIfThenElsePage.vlt | com.assentis.cockpit.bo.BoIfThenElsePage
=====================================================================
This is the parent element of an Assentis DocDesign IF / Conditional element.
It has a name and a condition expressed via XPath, and holds up to two child elements
representing the THEN branch and the ELSE branch. Depending on the value the given
XPath expression evaluates to, up to one of the branches will be processed.
Configurables:
-------------------------------------
name=HitAssIfCondition
xpathExpression=/xml/gender/text()='f'


TemplateIfThenPage.vlt | com.assentis.cockpit.bo.BoIfThenPage


TemplateDocumentVariable.vlt | com.assentis.cockpit.bo.BoDocumentVariable
==========================================================================
This is appearently a special DocFamily XSLT extension (?)
A variable abstraction, that enables creating, setting and reading of variables within DocFamily Workspaces.
Configurables:
-------------------------------------
name=weight             the name of the BoDocumentVariable Object in the DocDesign workspace
variableName='weight'   the name of the variable. This is an XPath expression, thus string values have to be
                        quoted like in 'weight' or 'name'.
variableValue='68px'    the value of the variable. Again, this is an XPath expression, thus string values have to be
                        quoted as in '68px' or 'Connor'.


TemplateNewLineHitCommand.vlt | com.assentis.cockpit.bo.BoAggregationParagraph holding
com.assentis.cockpit.bo.BoCReturn and com.assentis.cockpit.bo.BoElementRepetition
======================================================================================
In HIT/CLOU there is an VERSTAERKER option and a NewLine Command.
By given a certain number of VERSTAERKER one can say how many NewLine elements shall be added.
This TemplatNewLineHitCommand models this behavior by constructing a DocDesign Aggregation holding
a CReturn and a Repetition element.
Configurables:
-------------------------------------
repetitionExpression=5  this is the number of repetitions for the 'newline character'.
                        the given value is an xpath expression which will be evaluated by the xslt transformer.
                        the expression typically should be obtained using the toXPathString() method of some Expression
                        given here in the HIT/CLOU component.















