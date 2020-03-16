

Pod::Spec.new do |s|



  s.name         = "vdUmeng"
  s.version      = "1.0.0"
  s.summary      = "vd plugin."
  s.description  = <<-DESC
                    vd plugin.
                   DESC

  s.homepage     = "http://vd.cc"
  s.license      = "MIT"
  s.author             = { "ViewDesign" => "viewdesign@gmail.com" }
  s.source =  { :path => '.' }
  s.source_files  = "vdUmeng", "**/**/*.{h,m,mm,c}"
  s.exclude_files = "Source/Exclude"
  s.platform     = :ios, "8.0"
  s.requires_arc = true

  s.dependency 'WeexSDK'
  s.dependency 'vd'
  s.dependency 'WeexPluginLoader', '~> 0.0.1.9.1'
  s.dependency 'UMCCommon'
  s.dependency 'UMCSecurityPlugins'
  s.dependency 'UMCAnalytics'
  s.dependency 'UMCPush'
  s.dependency 'UMCCommonLog'

end
