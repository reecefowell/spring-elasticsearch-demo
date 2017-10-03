Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"

  config.vm.network "private_network", type: "dhcp"

  config.vm.define "elasticsearch" do |elasticsearch|
    elasticsearch.vm.hostname = "reecefowell-elasticsearch-demo"

    elasticsearch.vm.provider "virtualbox" do |v|
      v.name = "reecefowell-elasticsearch-demo"
      v.memory = 2048
    end

    elasticsearch.vm.provision "ansible" do |ansible|
      ansible.limit = "all,localhost"
      ansible.playbook = "ansible/vagrant.yml"
      ansible.groups = {
          "common" => ["elasticsearch"]
      }
    end
  end
end
