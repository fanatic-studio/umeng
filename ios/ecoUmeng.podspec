

Pod::Spec.new do |s|



  s.name         = "ecoUmeng"
  s.version      = "1.0.0"
  s.summary      = "eco plugin."
  s.description  = <<-DESC
                    eco plugin.
                   DESC

  s.homepage     = "http://eco.cc"
  s.license      = "MIT"
  s.author             = { "ViewDesign" => "viewdesign@gmail.com" }
  s.source =  { :path => '.' }
  s.source_files  = "ecoUmeng", "**/**/*.{h,m,mm,c}"
  s.exclude_files = "Source/Exclude"
  s.platform     = :ios, "8.0"
  s.requires_arc = true

  s.dependency 'WeexSDK'
  s.dependency 'eco'
  s.dependency 'WeexPluginLoader', '~> 0.0.1.9.1'
  s.dependency 'UMCCommon'
  s.dependency 'UMCSecurityPlugins'
  s.dependency 'UMCAnalytics'
  s.dependency 'UMCPush'
  s.dependency 'UMCCommonLog'

end
