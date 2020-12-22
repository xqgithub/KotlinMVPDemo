<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <@kt.addAllKotlinDependencies />
    <instantiate from="root/res/layout/fragment_blank.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml" />

    <!-- fragment -->
    <instantiate from="root/src/app_package/OneFragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/ui/fragments/${fragmentClass}.${ktOrJavaExt}" />
    <!-- view -->
    <instantiate from="root/src/app_package/OneFragmentView.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/views/${fragmentClass}View.${ktOrJavaExt}" />
    <!-- presenter -->
    <instantiate from="root/src/app_package/OneFragmentPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/mvp/presenters/${fragmentClass}Presenter.${ktOrJavaExt}" />
    <!-- scope -->
    <instantiate from="root/src/app_package/OneFragmentScope.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/scopes/${fragmentClass}Scope.${ktOrJavaExt}" />
    <!-- module -->
    <instantiate from="root/src/app_package/OneFragmentModule.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/modules/${fragmentClass}Module.${ktOrJavaExt}" />
    <!-- componet -->
    <instantiate from="root/src/app_package/OneFragmentComponet.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/di/componets/${fragmentClass}Componet.${ktOrJavaExt}" />
</recipe>
